package com.example.ui.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object AuthManager {

    /**
     * Traduction conviviale et informative des erreurs d'authentification en français et créole.
     */
    fun mapAuthException(e: Exception): String {
        return when (e) {
            is FirebaseAuthWeakPasswordException ->
                "Le mot de passe doit comporter au moins 6 caractères."
            is FirebaseAuthInvalidCredentialsException ->
                "Identifiants invalides : vérifiez votre compte ou réessayez avec Google."
            is FirebaseAuthUserCollisionException ->
                "Un compte existe déjà avec cette adresse e-mail. Veuillez utiliser la connexion Google."
            is FirebaseAuthInvalidUserException ->
                "Aucun compte n'est associé à cette adresse e-mail ou le compte a été désactivé."
            is FirebaseAuthActionCodeException ->
                "Code ou lien de vérification expiré ou déjà utilisé."
            is FirebaseNetworkException ->
                "Erreur réseau : veuillez vérifier votre connexion Internet."
            else -> {
                val msg = e.localizedMessage ?: ""
                if (msg.contains("CONFIGURATION_NOT_FOUND", ignoreCase = true) ||
                    msg.contains("OPERATION_NOT_ALLOWED", ignoreCase = true)
                ) {
                    "Se sèlman Google Sign-In ki otorize sou aplikasyon an. Tanpri itilize bouton Google la pou w konekte."
                } else {
                    msg.ifBlank { "Une erreur d'authentification est survenue. Veuillez vous connecter avec Google." }
                }
            }
        }
    }

    /**
     * Tentative de connexion automatique silencieuse au démarrage si l'utilisateur
     * possède déjà une session active ou un compte Google déjà autorisé.
     */
    fun attemptAutoSignIn(
        context: Context,
        credentialManager: CredentialManager,
        onAuthSuccess: () -> Unit,
        onUnauthenticated: () -> Unit,
        scope: CoroutineScope
    ) {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            onAuthSuccess()
            return
        }

        val clientId = try {
            context.getString(R.string.default_web_client_id)
        } catch (e: Exception) {
            Log.w("AuthManager", "default_web_client_id not found: ${e.message}")
            onUnauthenticated()
            return
        }

        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(clientId)
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        scope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
                    val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                    auth.signInWithCredential(authCredential).await()
                    onAuthSuccess()
                } else {
                    onUnauthenticated()
                }
            } catch (e: Exception) {
                onUnauthenticated()
            }
        }
    }

    /**
     * Connexion interactive officielle avec Google Sign-In via Credential Manager.
     */
    fun onGoogleSignInClicked(
        context: Context,
        credentialManager: CredentialManager,
        onAuthSuccess: () -> Unit,
        onAuthError: (String) -> Unit,
        scope: CoroutineScope,
        onAuthCancelled: () -> Unit = {}
    ) {
        val auth = FirebaseAuth.getInstance()
        val clientId = try {
            context.getString(R.string.default_web_client_id)
        } catch (e: Exception) {
            onAuthError("Konfigirasyon Google Sign-In manke (default_web_client_id)")
            return
        }

        val signInOption = GetSignInWithGoogleOption.Builder(serverClientId = clientId).build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInOption)
            .build()

        val activity = context as? Activity
        if (activity == null) {
            onAuthError("Kontèks aktivite a pa disponib pou otantifikasyon an.")
            return
        }

        scope.launch {
            try {
                val result = credentialManager.getCredential(activity, request)
                val credential = result.credential
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
                    val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                    auth.signInWithCredential(authCredential).await()
                    onAuthSuccess()
                } else {
                    onAuthError("Kalite idantifyan Google inatandi.")
                }
            } catch (e: GetCredentialCancellationException) {
                Log.w("Auth", "Google Sign-In flow cancelled or dismissed: ${e.message}", e)
                onAuthCancelled()
            } catch (e: Exception) {
                Log.e("Auth", "Google Sign-In failed", e)
                onAuthError(mapAuthException(e))
            }
        }
    }

    /**
     * Déconnexion complète de Firebase Auth et effacement des identifiants Credential Manager.
     */
    fun signOut(
        context: Context,
        credentialManager: CredentialManager,
        onSignOutComplete: () -> Unit,
        scope: CoroutineScope
    ) {
        FirebaseAuth.getInstance().signOut()
        scope.launch {
            try {
                credentialManager.clearCredentialState(ClearCredentialStateRequest())
            } catch (e: Exception) {
                Log.e("Auth", "Failed to clear credential state", e)
            } finally {
                onSignOutComplete()
            }
        }
    }
}
