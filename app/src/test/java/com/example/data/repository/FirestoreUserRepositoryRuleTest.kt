package com.example.data.repository

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.model.UserProfile
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FirestoreUserRepositoryRuleTest : FirestoreEmulatorTestBase() {

  @Test
  fun testStoreAndRetrieveUserProfileWithAllRequiredFields(): Unit = runBlocking {
    val email = "athlete_${System.currentTimeMillis()}@acashconnect.org"
    val uid = signInTestUser(email)

    val repository = FirestoreUserRepository(firestore, auth)

    val testProfile = UserProfile(
      id = 1L,
      fullName = "Kofi Mensah",
      email = email,
      phone = "+33 7 89 01 23 45",
      bloodGroup = "AB+",
      restingBpm = 54,
      maxBpm = 196,
      heightCm = 195f,
      weightKg = 92.5f,
      gender = "Masculin",
      role = "Athlète",
      positionOrSpecialty = "Intérieur / Rebondeur",
      teamOrClub = "Acash 3x3 Elite",
      bio = "Pivot 3x3 puissant avec grosse détente verticale.",
      city = "Lyon, France",
      fibaRanking = 1850,
      isCurrentUser = true
    )

    // 1. Store profile
    val saveResult = repository.saveUserProfile(testProfile, uid)
    assertTrue("Save profile should succeed", saveResult.isSuccess)

    // 2. Retrieve profile
    val fetchResult = repository.getUserProfile(uid)
    assertTrue("Get profile should succeed", fetchResult.isSuccess)

    val fetched = fetchResult.getOrNull()
    assertNotNull("Fetched profile must not be null", fetched)

    // Verify all specified profile fields are saved and retrieved correctly
    assertEquals(testProfile.email, fetched!!.email)
    assertEquals(testProfile.phone, fetched.phone)
    assertEquals(testProfile.bloodGroup, fetched.bloodGroup)
    assertEquals(testProfile.restingBpm, fetched.restingBpm)
    assertEquals(testProfile.maxBpm, fetched.maxBpm)
    assertEquals(testProfile.heightCm, fetched.heightCm, 0.1f)
    assertEquals(testProfile.weightKg, fetched.weightKg, 0.1f)
    assertEquals(testProfile.gender, fetched.gender)
    assertEquals(testProfile.fullName, fetched.fullName)
    assertEquals(testProfile.role, fetched.role)
    assertEquals(testProfile.positionOrSpecialty, fetched.positionOrSpecialty)
    assertEquals(testProfile.teamOrClub, fetched.teamOrClub)
    assertEquals(testProfile.bio, fetched.bio)
    assertEquals(testProfile.city, fetched.city)
  }

  @Test
  fun testCrossUserAccessDenied(): Unit = runBlocking {
    val uidA = signInTestUser("user_a_${System.currentTimeMillis()}@acash.org")
    val repositoryA = FirestoreUserRepository(firestore, auth)

    val profileA = UserProfile(
      fullName = "User A",
      email = "a@acash.org",
      phone = "0123456789",
      bloodGroup = "O+",
      restingBpm = 60,
      maxBpm = 190,
      heightCm = 190f,
      weightKg = 85f,
      gender = "Masculin"
    )
    val saveA = repositoryA.saveUserProfile(profileA, uidA)
    assertTrue(saveA.isSuccess)

    // Now sign in as User B
    val uidB = signInTestUser("user_b_${System.currentTimeMillis()}@acash.org")
    val repositoryB = FirestoreUserRepository(firestore, auth)

    // User B tries to read User A's profile directly via firestore -> rules forbid it
    val fetchResult = repositoryB.getUserProfile(uidA)
    assertTrue("Fetch of another user's profile should fail or return null due to security rules", fetchResult.isFailure)
  }

  @Test
  fun testUpdateAthleticDetailsInFirestore(): Unit = runBlocking {
    val email = "athletic_input_${System.currentTimeMillis()}@acash.org"
    val uid = signInTestUser(email)
    val repository = FirestoreUserRepository(firestore, auth)

    val initialProfile = UserProfile(
      fullName = "Test Athlete",
      email = email,
      phone = "+33600000000",
      bloodGroup = "O+",
      restingBpm = 65,
      maxBpm = 185,
      heightCm = 185f,
      weightKg = 80f,
      gender = "Masculin"
    )
    repository.saveUserProfile(initialProfile, uid)

    // Update with new athletic details: Blood Type B-, Resting BPM 52, Max BPM 198, Height 198cm, Weight 94kg, Gender Féminin
    val updatedAthletic = initialProfile.copy(
      bloodGroup = "B-",
      restingBpm = 52,
      maxBpm = 198,
      heightCm = 198f,
      weightKg = 94f,
      gender = "Féminin"
    )
    val updateResult = repository.saveUserProfile(updatedAthletic, uid)
    assertTrue("Update of athletic details should succeed", updateResult.isSuccess)

    val fetched = repository.getUserProfile(uid).getOrNull()
    assertNotNull(fetched)
    assertEquals("B-", fetched!!.bloodGroup)
    assertEquals(52, fetched.restingBpm)
    assertEquals(198, fetched.maxBpm)
    assertEquals(198f, fetched.heightCm, 0.1f)
    assertEquals(94f, fetched.weightKg, 0.1f)
    assertEquals("Féminin", fetched.gender)
  }

  @Test
  fun testAthleteProfileWithSkillsPositionsAndExperience(): Unit = runBlocking {
    val email = "athlete_skills_${System.currentTimeMillis()}@acash.org"
    val uid = signInTestUser(email)
    val repository = FirestoreUserRepository(firestore, auth)

    val athleteProfile = UserProfile(
      fullName = "Tariq Owens",
      email = email,
      phone = "+33611223344",
      bloodGroup = "A+",
      restingBpm = 50,
      maxBpm = 202,
      heightCm = 204f,
      weightKg = 98f,
      gender = "Masculin",
      role = "Athlète",
      primaryPosition = "Intérieur / Rebondeur",
      secondaryPosition = "Pivot 3x3",
      positions = listOf("Intérieur", "Pivot", "Rim Protector"),
      skillLevel = "Élite / FIBA World Tour",
      shootingSkill = 75,
      defenseSkill = 92,
      playmakingSkill = 70,
      physicalConditioningSkill = 95,
      tacticalIqSkill = 88,
      experienceYears = 9,
      competitionLevel = "FIBA 3x3 World Tour Pro",
      pastClubsOrTeams = "Paris 3x3 Hoops, Monaco Basket",
      achievements = "Finaliste World Tour Utsunomiya, 3x Champion National 3x3"
    )

    val saveResult = repository.saveUserProfile(athleteProfile, uid)
    assertTrue("Saving athlete profile with skills and experience should succeed", saveResult.isSuccess)

    val fetched = repository.getUserProfile(uid).getOrNull()
    assertNotNull(fetched)
    assertEquals("Tariq Owens", fetched!!.fullName)
    assertEquals("Athlète", fetched.role)
    assertEquals("Intérieur / Rebondeur", fetched.primaryPosition)
    assertEquals("Pivot 3x3", fetched.secondaryPosition)
    assertEquals(3, fetched.positions.size)
    assertEquals("Élite / FIBA World Tour", fetched.skillLevel)
    assertEquals(75, fetched.shootingSkill)
    assertEquals(92, fetched.defenseSkill)
    assertEquals(70, fetched.playmakingSkill)
    assertEquals(95, fetched.physicalConditioningSkill)
    assertEquals(88, fetched.tacticalIqSkill)
    assertEquals(9, fetched.experienceYears)
    assertEquals("FIBA 3x3 World Tour Pro", fetched.competitionLevel)
    assertEquals("Paris 3x3 Hoops, Monaco Basket", fetched.pastClubsOrTeams)
    assertEquals("Finaliste World Tour Utsunomiya, 3x Champion National 3x3", fetched.achievements)
  }

  @Test
  fun testCoachProfileWithCertificationsAndPhilosophy(): Unit = runBlocking {
    val email = "coach_${System.currentTimeMillis()}@acash.org"
    val uid = signInTestUser(email)
    val repository = FirestoreUserRepository(firestore, auth)

    val coachProfile = UserProfile(
      fullName = "Sarah Lefevre",
      email = email,
      phone = "+33699887766",
      bloodGroup = "O-",
      restingBpm = 58,
      maxBpm = 180,
      heightCm = 178f,
      weightKg = 68f,
      gender = "Féminin",
      role = "Coach",
      primaryPosition = "Entraîneur Principal 3x3",
      secondaryPosition = "Spécialiste Tir & Shot Clock",
      positions = listOf("Head Coach", "Tir"),
      skillLevel = "Professionnel / International",
      shootingSkill = 90,
      defenseSkill = 85,
      playmakingSkill = 90,
      physicalConditioningSkill = 80,
      tacticalIqSkill = 98,
      experienceYears = 14,
      competitionLevel = "Équipe de France 3x3 & FIBA Instructor",
      pastClubsOrTeams = "FFBB Pôle France, Bourges Basket",
      achievements = "Médaille d'Or Coupe d'Europe 3x3, Coach de l'Année 2022",
      certifications = "FIBA 3x3 Certified Instructor, Brevet d'État 2ème Degré",
      coachingPhilosophy = "Pression défensive tout-terrain, transition sous 4 secondes et volume de tir derrière l'arc.",
      hourlyRate = "65 € / séance"
    )

    val saveResult = repository.saveUserProfile(coachProfile, uid)
    assertTrue("Saving coach profile should succeed", saveResult.isSuccess)

    val fetched = repository.getUserProfile(uid).getOrNull()
    assertNotNull(fetched)
    assertEquals("Sarah Lefevre", fetched!!.fullName)
    assertEquals("Coach", fetched.role)
    assertTrue(fetched.isCoach)
    assertEquals("FIBA 3x3 Certified Instructor, Brevet d'État 2ème Degré", fetched.certifications)
    assertEquals("Pression défensive tout-terrain, transition sous 4 secondes et volume de tir derrière l'arc.", fetched.coachingPhilosophy)
    assertEquals("65 € / séance", fetched.hourlyRate)
  }

  @Test
  fun testUpdateSkillLevelsAndPositionsIndividually(): Unit = runBlocking {
    val email = "update_test_${System.currentTimeMillis()}@acash.org"
    val uid = signInTestUser(email)
    val repository = FirestoreUserRepository(firestore, auth)

    val baseProfile = UserProfile(
      fullName = "Lucas Moreau",
      email = email,
      phone = "+33622334455",
      bloodGroup = "B+",
      restingBpm = 64,
      maxBpm = 192,
      heightCm = 192f,
      weightKg = 86f,
      gender = "Masculin"
    )
    repository.saveUserProfile(baseProfile, uid)

    // Update positions
    val posResult = repository.updatePositions(
      userId = uid,
      primaryPosition = "Meneur Créateur",
      secondaryPosition = "Sniper 2-pts",
      positions = listOf("Meneur", "Arrière")
    )
    assertTrue(posResult.isSuccess)

    // Update skills
    val skillsResult = repository.updateSkillLevels(
      userId = uid,
      skillLevel = "Confirmé Régional",
      shootingSkill = 86,
      defenseSkill = 80,
      playmakingSkill = 91,
      physicalConditioningSkill = 87,
      tacticalIqSkill = 84
    )
    assertTrue(skillsResult.isSuccess)

    // Update experience
    val expResult = repository.updateBasketballExperience(
      userId = uid,
      experienceYears = 8,
      competitionLevel = "Nationale 3 & Circuits Open Plus",
      pastClubsOrTeams = "Nanterre Basket",
      achievements = "Champion IDF 3x3"
    )
    assertTrue(expResult.isSuccess)

    val fetched = repository.getUserProfile(uid).getOrNull()
    assertNotNull(fetched)
    assertEquals("Meneur Créateur", fetched!!.primaryPosition)
    assertEquals("Sniper 2-pts", fetched.secondaryPosition)
    assertEquals("Confirmé Régional", fetched.skillLevel)
    assertEquals(86, fetched.shootingSkill)
    assertEquals(91, fetched.playmakingSkill)
    assertEquals(8, fetched.experienceYears)
    assertEquals("Nationale 3 & Circuits Open Plus", fetched.competitionLevel)
  }

  @Test
  fun testSuperAdminCanModifyAllProfiles(): Unit = runBlocking {
    // 1. Create Athlete profile by athlete
    val athleteEmail = "atlet_${System.currentTimeMillis()}@acash.org"
    val athleteUid = signInTestUser(athleteEmail)
    val athleteRepo = FirestoreUserRepository(firestore, auth)
    val athleteProfile = UserProfile(
      fullName = "Jean Athlete",
      email = athleteEmail,
      phone = "+50930000001",
      bloodGroup = "O+",
      role = "Athlète"
    )
    athleteRepo.saveUserProfile(athleteProfile, athleteUid)

    // 2. Sign in as Super Admin (bassianysma@gmail.com) and create Super Admin account
    val adminUid = signInTestUser(UserProfile.SUPER_ADMIN_EMAIL)
    val adminRepo = FirestoreUserRepository(firestore, auth)
    val seedResult = adminRepo.seedSuperAdminProfile(UserProfile.SUPER_ADMIN_EMAIL, adminUid)
    assertTrue("Seed failed: ${seedResult.exceptionOrNull()?.message}", seedResult.isSuccess)

    // Super Admin updates the athlete profile
    val modifiedAthlete = athleteProfile.copy(
      fullName = "Jean Athlete Modifié pa Admin",
      heightCm = 199f,
      notes = "Validé pa Super Admin Bassian Ysma"
    )
    val adminUpdateResult = adminRepo.saveUserProfile(modifiedAthlete, athleteUid)
    assertTrue("Super Admin should be able to modify athlete profile: ${adminUpdateResult.exceptionOrNull()?.message}", adminUpdateResult.isSuccess)

    // Verify fetched data
    val fetchedAthlete = adminRepo.getUserProfile(athleteUid).getOrNull()
    assertNotNull(fetchedAthlete)
    assertEquals("Jean Athlete Modifié pa Admin", fetchedAthlete!!.fullName)
    assertEquals("Validé pa Super Admin Bassian Ysma", fetchedAthlete.notes)

    // 3. Super Admin creates and modifies a Parent profile
    val parentUid = "parent_${System.currentTimeMillis()}"
    val parentProfile = UserProfile(
      fullName = "Madame Parent",
      email = "parent@acash.org",
      phone = "+50930000002",
      role = "Parent",
      notes = "Paran akonpagnatè"
    )
    val adminSaveParentResult = adminRepo.saveUserProfile(parentProfile, parentUid)
    assertTrue("Super Admin should be able to create and modify Parent profile", adminSaveParentResult.isSuccess)

    // 4. Super Admin creates and modifies a Supporter profile
    val supporterUid = "supporter_${System.currentTimeMillis()}"
    val supporterProfile = UserProfile(
      fullName = "Luc Fanatik",
      email = "fan@acash.org",
      phone = "+50930000003",
      role = "Supporter",
      notes = "Manm VIP Klib Sipòtè"
    )
    val adminSaveSupporterResult = adminRepo.saveUserProfile(supporterProfile, supporterUid)
    assertTrue("Super Admin should be able to create and modify Supporter profile", adminSaveSupporterResult.isSuccess)
  }

  @Test
  fun testCoachCanModifyAthleteProfile(): Unit = runBlocking {
    // 1. Create Athlete profile
    val athleteEmail = "atlet_coach_test_${System.currentTimeMillis()}@acash.org"
    val athleteUid = signInTestUser(athleteEmail)
    val athleteRepo = FirestoreUserRepository(firestore, auth)
    val athleteProfile = UserProfile(
      fullName = "Kofi Joueur",
      email = athleteEmail,
      phone = "+33688990011",
      bloodGroup = "A+",
      role = "Athlète",
      shootingSkill = 70,
      defenseSkill = 75
    )
    athleteRepo.saveUserProfile(athleteProfile, athleteUid)

    // 2. Create and sign in as Coach
    val coachEmail = "coach_test_${System.currentTimeMillis()}@acash.org"
    val coachUid = signInTestUser(coachEmail)
    val coachRepo = FirestoreUserRepository(firestore, auth)
    val coachProfile = UserProfile(
      fullName = "Coach Marcus",
      email = coachEmail,
      phone = "+33699001122",
      role = "Coach"
    )
    coachRepo.saveUserProfile(coachProfile, coachUid)

    // 3. Coach modifies athlete's profile (skills and notes)
    val coachUpdatedAthlete = athleteProfile.copy(
      shootingSkill = 85,
      defenseSkill = 88,
      notes = "Mise à jour par Coach Marcus : progression tir 2-pts validée"
    )
    val coachUpdateResult = coachRepo.saveUserProfile(coachUpdatedAthlete, athleteUid)
    assertTrue("Coach should be able to modify athlete's profile", coachUpdateResult.isSuccess)

    val fetched = coachRepo.getUserProfile(athleteUid).getOrNull()
    assertNotNull(fetched)
    assertEquals(85, fetched!!.shootingSkill)
    assertEquals(88, fetched.defenseSkill)
    assertEquals("Mise à jour par Coach Marcus : progression tir 2-pts validée", fetched.notes)
  }
}
