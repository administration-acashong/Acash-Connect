package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.FirestoreUserProfile
import com.example.data.model.UserProfile
import com.example.data.remote.OperationType
import com.example.data.remote.handleFirestoreError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

/**
 * Repository gérant la persistance et les opérations CRUD Firestore
 * pour les profils Athlètes et Coachs d'Acash Connect.
 */
class FirestoreUserRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        ),
        FirebaseAuth.getInstance()
    )

    private val usersCollection = firestore.collection("users")

    fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be authenticated before accessing Firestore.")
    }

    /**
     * Observer en temps réel le profil utilisateur dans Firestore
     */
    fun observeUserProfile(userId: String): Flow<UserProfile?> = flow {
        val docPath = "users/$userId"
        val docRef = usersCollection.document(userId)

        docRef.snapshots()
            .map { snapshot ->
                if (snapshot.exists()) {
                    snapshot.toObject(FirestoreUserProfile::class.java)?.toUserProfile()
                } else {
                    null
                }
            }
            .catch { error ->
                if (error is Exception) {
                    handleFirestoreError(error, OperationType.GET, docPath)
                }
                throw error
            }
            .collect { emit(it) }
    }

    /**
     * Récupérer le profil utilisateur (athlète ou coach) une seule fois
     */
    suspend fun getUserProfile(userId: String): Result<UserProfile?> {
        val docPath = "users/$userId"
        return try {
            val docRef = usersCollection.document(userId)
            val snapshot = docRef.get().await()
            if (snapshot.exists()) {
                val firestoreModel = snapshot.toObject(FirestoreUserProfile::class.java)
                Result.success(firestoreModel?.toUserProfile())
            } else {
                Result.success(null)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.GET, docPath)
            Result.failure(e)
        }
    }

    /**
     * Enregistrer ou mettre à jour un profil complet (athlète ou coach) dans Firestore,
     * incluant les métriques athlétiques, les postes, les niveaux de compétences et l'expérience.
     */
    suspend fun saveUserProfile(profile: UserProfile, userId: String): Result<Unit> {
        val docPath = "users/$userId"
        val docRef = usersCollection.document(userId)

        return try {
            val existing = docRef.get().await()
            val payload = mutableMapOf<String, Any>(
                "userId" to userId,
                "fullName" to profile.fullName,
                "email" to profile.email,
                "phone" to profile.phone,
                "bloodGroup" to profile.bloodGroup,
                "restingBpm" to profile.restingBpm.toLong(),
                "maxBpm" to profile.maxBpm.toLong(),
                "heightCm" to profile.heightCm.toDouble(),
                "weightKg" to profile.weightKg.toDouble(),
                "gender" to profile.gender,
                "role" to profile.role,
                "positionOrSpecialty" to profile.positionOrSpecialty,
                "teamOrClub" to profile.teamOrClub,
                "bio" to profile.bio,
                "city" to profile.city,
                "fibaRanking" to profile.fibaRanking.toLong(),
                "selectedSport" to profile.selectedSport,
                "secondarySports" to profile.secondarySports,
                "selectedDepartment" to profile.selectedDepartment,
                "selectedCommune" to profile.selectedCommune,

                // Métriques FIBA 3x3
                "fieldGoalPercentage" to profile.fieldGoalPercentage.toDouble(),
                "twoPointPercentage" to profile.twoPointPercentage.toDouble(),
                "reboundsPerGame" to profile.reboundsPerGame.toDouble(),
                "assists" to profile.assists.toLong(),
                "assistsPerGame" to profile.assistsPerGame.toDouble(),
                "gamesPlayed" to profile.gamesPlayed.toLong(),
                "pointsPerGame" to profile.pointsPerGame.toDouble(),

                // Postes & Spécialisations Basketball
                "primaryPosition" to profile.primaryPosition,
                "secondaryPosition" to profile.secondaryPosition,
                "positions" to profile.positions,

                // Niveaux de compétences (Skill Levels)
                "skillLevel" to profile.skillLevel,
                "shootingSkill" to profile.shootingSkill.toLong(),
                "defenseSkill" to profile.defenseSkill.toLong(),
                "playmakingSkill" to profile.playmakingSkill.toLong(),
                "physicalConditioningSkill" to profile.physicalConditioningSkill.toLong(),
                "tacticalIqSkill" to profile.tacticalIqSkill.toLong(),

                // Expérience Basketball
                "experienceYears" to profile.experienceYears.toLong(),
                "competitionLevel" to profile.competitionLevel,
                "pastClubsOrTeams" to profile.pastClubsOrTeams,
                "achievements" to profile.achievements,

                // Spécificités Coach
                "certifications" to profile.certifications,
                "coachingPhilosophy" to profile.coachingPhilosophy,
                "hourlyRate" to profile.hourlyRate,
                "notes" to profile.notes,

                "updatedAt" to FieldValue.serverTimestamp()
            )

            if (!existing.exists() || existing.get("createdAt") == null) {
                payload["createdAt"] = FieldValue.serverTimestamp()
            } else {
                existing.get("createdAt")?.let { payload["createdAt"] = it }
            }

            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docPath)
            Result.failure(e)
        }
    }

    /**
     * Mettre à jour spécifiquement les postes d'un athlète ou coach
     */
    suspend fun updatePositions(
        userId: String,
        primaryPosition: String,
        secondaryPosition: String,
        positions: List<String>
    ): Result<Unit> {
        val docPath = "users/$userId"
        val docRef = usersCollection.document(userId)

        return try {
            val updates = mapOf(
                "primaryPosition" to primaryPosition,
                "secondaryPosition" to secondaryPosition,
                "positions" to positions,
                "positionOrSpecialty" to primaryPosition,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.update(updates).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docPath)
            Result.failure(e)
        }
    }

    /**
     * Mettre à jour les compétences et niveaux de jeu (Skill Levels)
     */
    suspend fun updateSkillLevels(
        userId: String,
        skillLevel: String,
        shootingSkill: Int,
        defenseSkill: Int,
        playmakingSkill: Int,
        physicalConditioningSkill: Int,
        tacticalIqSkill: Int
    ): Result<Unit> {
        val docPath = "users/$userId"
        val docRef = usersCollection.document(userId)

        return try {
            val updates = mapOf(
                "skillLevel" to skillLevel,
                "shootingSkill" to shootingSkill.toLong(),
                "defenseSkill" to defenseSkill.toLong(),
                "playmakingSkill" to playmakingSkill.toLong(),
                "physicalConditioningSkill" to physicalConditioningSkill.toLong(),
                "tacticalIqSkill" to tacticalIqSkill.toLong(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.update(updates).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docPath)
            Result.failure(e)
        }
    }

    /**
     * Mettre à jour l'expérience sportive basketball (années, clubs, palmarès)
     */
    suspend fun updateBasketballExperience(
        userId: String,
        experienceYears: Int,
        competitionLevel: String,
        pastClubsOrTeams: String,
        achievements: String
    ): Result<Unit> {
        val docPath = "users/$userId"
        val docRef = usersCollection.document(userId)

        return try {
            val updates = mapOf(
                "experienceYears" to experienceYears.toLong(),
                "competitionLevel" to competitionLevel,
                "pastClubsOrTeams" to pastClubsOrTeams,
                "achievements" to achievements,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.update(updates).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docPath)
            Result.failure(e)
        }
    }

    /**
     * Mettre à jour les informations spécifiques au profil Coach
     */
    suspend fun updateCoachDetails(
        userId: String,
        certifications: String,
        coachingPhilosophy: String,
        hourlyRate: String
    ): Result<Unit> {
        val docPath = "users/$userId"
        val docRef = usersCollection.document(userId)

        return try {
            val updates = mapOf(
                "role" to "Coach",
                "certifications" to certifications,
                "coachingPhilosophy" to coachingPhilosophy,
                "hourlyRate" to hourlyRate,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.update(updates).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docPath)
            Result.failure(e)
        }
    }

    /**
     * Mettre à jour le rôle (Athlète ou Coach)
     */
    suspend fun updateRole(userId: String, role: String): Result<Unit> {
        val docPath = "users/$userId"
        val docRef = usersCollection.document(userId)

        return try {
            val updates = mapOf(
                "role" to role,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.update(updates).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docPath)
            Result.failure(e)
        }
    }

    /**
     * Mettre à jour les métriques de performance 3x3 directement liées au profil
     */
    suspend fun updatePerformanceMetrics(
        userId: String,
        fgPercentage: Double,
        twoPointPercentage: Double,
        reboundsPerGame: Double,
        assists: Long,
        assistsPerGame: Double,
        gamesPlayed: Long,
        pointsPerGame: Double
    ): Result<Unit> {
        val docPath = "users/$userId"
        val docRef = usersCollection.document(userId)

        return try {
            val updates = mapOf(
                "fieldGoalPercentage" to fgPercentage,
                "twoPointPercentage" to twoPointPercentage,
                "reboundsPerGame" to reboundsPerGame,
                "assists" to assists,
                "assistsPerGame" to assistsPerGame,
                "gamesPlayed" to gamesPlayed,
                "pointsPerGame" to pointsPerGame,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.update(updates).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docPath)
            Result.failure(e)
        }
    }

    /**
     * Supprimer le profil utilisateur dans Firestore
     */
    suspend fun deleteUserProfile(userId: String): Result<Unit> {
        val docPath = "users/$userId"
        val docRef = usersCollection.document(userId)

        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docPath)
            Result.failure(e)
        }
    }

    /**
     * Récupérer tous les profils d'utilisateurs disponibles dans Firestore
     * (Autorisé pour les Admins, et pour les Coachs filtrant les athlètes)
     */
    suspend fun getAllUsers(): Result<List<UserProfile>> {
        return try {
            val snapshot = usersCollection.get().await()
            val list = snapshot.documents.mapNotNull { doc ->
                doc.toObject(FirestoreUserProfile::class.java)?.toUserProfile()
            }
            Result.success(list)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Permet à un Coach de modifier un Athlète ou à un Super Admin de modifier n'importe quel profil (Paran, Atlet, Antrene, Sipote)
     */
    suspend fun saveAnyUserProfile(profile: UserProfile, targetUserId: String): Result<Unit> {
        return saveUserProfile(profile, targetUserId)
    }

    /**
     * Initialiser le profil Super Admin avec l'email bassianysma@gmail.com si nécessaire
     */
    suspend fun seedSuperAdminProfile(
        adminEmail: String = UserProfile.SUPER_ADMIN_EMAIL,
        adminUid: String
    ): Result<Unit> {
        val superAdminProfile = UserProfile(
            fullName = "Bassian Ysma (Super Admin)",
            email = adminEmail,
            phone = "+33 6 00 00 00 00",
            role = "Super Admin",
            positionOrSpecialty = "Administrateur Général & Direction FIBA 3x3",
            bio = "Compte Super Administrateur Acash Connect avec tous les droits de gestion et de modification pour les Athlètes, Coachs, Parents et Supporters.",
            teamOrClub = "Direction Acash Connect",
            city = "Paris & Port-au-Prince"
        )
        return saveUserProfile(superAdminProfile, adminUid)
    }
}
