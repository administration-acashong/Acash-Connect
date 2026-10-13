package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Acash Connect", appName)
  }

  @Test
  fun `test auth exception french translations`() {
    val weakPass = com.google.firebase.auth.FirebaseAuthWeakPasswordException("WEAK_PASSWORD", "Weak password", null)
    val msg = com.example.ui.auth.AuthManager.mapAuthException(weakPass)
    assertEquals("Le mot de passe doit comporter au moins 6 caractères.", msg)
  }
}
