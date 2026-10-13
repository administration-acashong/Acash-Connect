package com.example

import com.example.data.model.Fiba3x3Repository
import com.example.data.model.UserProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun testUserProfileBmiAndBpmCalculations() {
    val profile = UserProfile(
      heightCm = 200f,
      weightKg = 100f,
      restingBpm = 58,
      maxBpm = 190
    )
    // BMI = 100 / (2.0 * 2.0) = 25.0
    assertEquals(25.0f, profile.bmi, 0.01f)
    assertEquals("Masse athlétique / Puissance", profile.bmiCategory)
    // Zone 85% - 95% : 161 - 180
    assertTrue(profile.targetZone3x3Bpm.contains("161"))
    assertTrue(profile.targetZone3x3Bpm.contains("180"))
  }

  @Test
  fun testFibaRulesRepositoryIntegrity() {
    val rules = Fiba3x3Repository.officialRules
    assertTrue(rules.isNotEmpty())
    val shotClockRule = rules.find { it.articleNumber.contains("Art. 5") }
    assertNotNull(shotClockRule)
    assertTrue(shotClockRule!!.keyPoints.any { it.contains("12 secondes") })

    val scoringRule = rules.find { it.articleNumber.contains("Art. 6") }
    assertNotNull(scoringRule)
    assertTrue(scoringRule!!.keyPoints.any { it.contains("21 points") })
  }
}
