package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.MainAppContainer
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AcashViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
  private val viewModel: AcashViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        AppContent(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun AppContent(viewModel: AcashViewModel) {
  var currentUser by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }

  DisposableEffect(Unit) {
    val listener = FirebaseAuth.AuthStateListener { auth ->
      currentUser = auth.currentUser
      auth.currentUser?.let { user ->
        viewModel.syncFromFirestore(user.uid)
      }
    }
    FirebaseAuth.getInstance().addAuthStateListener(listener)
    onDispose {
      FirebaseAuth.getInstance().removeAuthStateListener(listener)
    }
  }

  if (currentUser == null) {
    AuthScreen(
      onAuthSuccess = {
        currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
          viewModel.syncFromFirestore(user.uid)
        }
      }
    )
  } else {
    MainAppContainer(
      viewModel = viewModel,
      onSignOut = {
        currentUser = null
      }
    )
  }
}
