package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Coach
import com.example.data.model.CoachSession
import com.example.data.model.Post
import com.example.data.model.PostCategory
import com.example.data.model.UserProfile
import com.example.data.repository.AcashRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class ScoreboardState(
    val teamAName: String = "Équipe A",
    val teamBName: String = "Équipe B",
    val scoreA: Int = 0,
    val scoreB: Int = 0,
    val foulsA: Int = 0,
    val foulsB: Int = 0,
    val matchSecondsRemaining: Int = 600, // 10 minutes FIBA
    val shotClockSeconds: Int = 12, // 12 secondes FIBA Shot Clock
    val isMatchRunning: Boolean = false,
    val isShotClockRunning: Boolean = false,
    val isGameOver: Boolean = false,
    val winnerNotice: String? = null,
    val buzzerAlert: String? = null
)

class AcashViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AcashRepository(application.applicationContext)

    val userProfile: StateFlow<UserProfile> = repository.userProfile
    val coaches: StateFlow<List<Coach>> = repository.coaches
    val sessions: StateFlow<List<CoachSession>> = repository.sessions
    val communityUsers: StateFlow<List<UserProfile>> = repository.communityUsers

    private val _selectedCategory = MutableStateFlow(PostCategory.ALL)
    val selectedCategory: StateFlow<PostCategory> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val filteredPosts: StateFlow<List<Post>> = combine(
        repository.posts,
        _selectedCategory,
        _searchQuery
    ) { posts, category, query ->
        posts.filter { post ->
            val matchCategory = if (category == PostCategory.ALL) true else post.category == category
            val matchQuery = if (query.isBlank()) true else {
                post.content.contains(query, ignoreCase = true) ||
                post.authorName.contains(query, ignoreCase = true) ||
                (post.highlightTag?.contains(query, ignoreCase = true) == true)
            }
            matchCategory && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Scoreboard 3x3 FIBA State
    private val _scoreboard = MutableStateFlow(ScoreboardState())
    val scoreboard: StateFlow<ScoreboardState> = _scoreboard.asStateFlow()

    private val _syncStatus = MutableStateFlow<String?>(null)
    val syncStatus: StateFlow<String?> = _syncStatus.asStateFlow()

    private var matchTimerJob: Job? = null
    private var shotClockJob: Job? = null

    fun updateProfile(profile: UserProfile) {
        repository.saveUserProfile(profile)
        // Also sync to Firebase Firestore if authenticated
        val currentUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                val result = repository.saveUserProfileToFirestore(profile, currentUid)
                if (result.isSuccess) {
                    _syncStatus.value = "Profil synchronisé avec Firebase Firestore"
                }
            }
        }
    }

    fun saveAthleticDetails(
        bloodType: String,
        restingBpm: Int,
        maxBpm: Int,
        heightCm: Float,
        weightKg: Float,
        gender: String,
        onComplete: (Boolean, String) -> Unit
    ) {
        val current = userProfile.value
        val updated = current.copy(
            bloodGroup = bloodType,
            restingBpm = restingBpm,
            maxBpm = maxBpm,
            heightCm = heightCm,
            weightKg = weightKg,
            gender = gender
        )
        repository.saveUserProfile(updated)

        val currentUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                val result = repository.saveUserProfileToFirestore(updated, currentUid)
                if (result.isSuccess) {
                    _syncStatus.value = "Données athlétiques enregistrées dans Firestore"
                    onComplete(true, "Données enregistrées dans Firestore avec succès !")
                } else {
                    onComplete(false, result.exceptionOrNull()?.localizedMessage ?: "Erreur d'enregistrement Firestore")
                }
            }
        } else {
            onComplete(true, "Données enregistrées avec succès !")
        }
    }

    fun savePerformanceMetrics(
        fgPercentage: Float,
        twoPointPercentage: Float,
        reboundsPerGame: Float,
        assists: Int,
        assistsPerGame: Float,
        gamesPlayed: Int,
        pointsPerGame: Float,
        onComplete: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        val current = userProfile.value
        val updated = current.copy(
            fieldGoalPercentage = fgPercentage,
            twoPointPercentage = twoPointPercentage,
            reboundsPerGame = reboundsPerGame,
            assists = assists,
            assistsPerGame = assistsPerGame,
            gamesPlayed = gamesPlayed,
            pointsPerGame = pointsPerGame
        )
        repository.saveUserProfile(updated)

        val currentUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                val result = repository.firestoreUserRepository.updatePerformanceMetrics(
                    userId = currentUid,
                    fgPercentage = fgPercentage.toDouble(),
                    twoPointPercentage = twoPointPercentage.toDouble(),
                    reboundsPerGame = reboundsPerGame.toDouble(),
                    assists = assists.toLong(),
                    assistsPerGame = assistsPerGame.toDouble(),
                    gamesPlayed = gamesPlayed.toLong(),
                    pointsPerGame = pointsPerGame.toDouble()
                )
                if (result.isSuccess) {
                    _syncStatus.value = "Statistiques 3x3 synchronisées avec Firestore"
                    onComplete(true, "Statistiques 3X3 enregistrées dans Firestore !")
                } else {
                    onComplete(false, result.exceptionOrNull()?.localizedMessage ?: "Erreur Firestore")
                }
            }
        } else {
            onComplete(true, "Statistiques enregistrées localement !")
        }
    }

    fun savePositions(
        primary: String,
        secondary: String,
        positionsList: List<String>,
        onComplete: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        val current = userProfile.value
        val updated = current.copy(
            primaryPosition = primary,
            secondaryPosition = secondary,
            positions = positionsList,
            positionOrSpecialty = primary
        )
        repository.saveUserProfile(updated)

        val currentUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                val result = repository.firestoreUserRepository.updatePositions(
                    userId = currentUid,
                    primaryPosition = primary,
                    secondaryPosition = secondary,
                    positions = positionsList
                )
                if (result.isSuccess) {
                    _syncStatus.value = "Postes 3x3 synchronisés avec Firestore"
                    onComplete(true, "Postes enregistrés dans Firestore !")
                } else {
                    onComplete(false, result.exceptionOrNull()?.localizedMessage ?: "Erreur Firestore")
                }
            }
        } else {
            onComplete(true, "Postes enregistrés localement !")
        }
    }

    fun saveSkillLevels(
        skillLevel: String,
        shooting: Int,
        defense: Int,
        playmaking: Int,
        conditioning: Int,
        tacticalIq: Int,
        onComplete: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        val current = userProfile.value
        val updated = current.copy(
            skillLevel = skillLevel,
            shootingSkill = shooting,
            defenseSkill = defense,
            playmakingSkill = playmaking,
            physicalConditioningSkill = conditioning,
            tacticalIqSkill = tacticalIq
        )
        repository.saveUserProfile(updated)

        val currentUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                val result = repository.firestoreUserRepository.updateSkillLevels(
                    userId = currentUid,
                    skillLevel = skillLevel,
                    shootingSkill = shooting,
                    defenseSkill = defense,
                    playmakingSkill = playmaking,
                    physicalConditioningSkill = conditioning,
                    tacticalIqSkill = tacticalIq
                )
                if (result.isSuccess) {
                    _syncStatus.value = "Compétences synchronisées avec Firestore"
                    onComplete(true, "Compétences enregistrées dans Firestore !")
                } else {
                    onComplete(false, result.exceptionOrNull()?.localizedMessage ?: "Erreur Firestore")
                }
            }
        } else {
            onComplete(true, "Compétences enregistrées localement !")
        }
    }

    fun saveBasketballExperience(
        years: Int,
        level: String,
        clubs: String,
        achievements: String,
        onComplete: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        val current = userProfile.value
        val updated = current.copy(
            experienceYears = years,
            competitionLevel = level,
            pastClubsOrTeams = clubs,
            achievements = achievements
        )
        repository.saveUserProfile(updated)

        val currentUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                val result = repository.firestoreUserRepository.updateBasketballExperience(
                    userId = currentUid,
                    experienceYears = years,
                    competitionLevel = level,
                    pastClubsOrTeams = clubs,
                    achievements = achievements
                )
                if (result.isSuccess) {
                    _syncStatus.value = "Expérience basket synchronisée avec Firestore"
                    onComplete(true, "Expérience enregistrée dans Firestore !")
                } else {
                    onComplete(false, result.exceptionOrNull()?.localizedMessage ?: "Erreur Firestore")
                }
            }
        } else {
            onComplete(true, "Expérience enregistrée localement !")
        }
    }

    fun saveCoachDetails(
        certifications: String,
        philosophy: String,
        hourlyRate: String,
        onComplete: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        val current = userProfile.value
        val updated = current.copy(
            role = "Coach",
            certifications = certifications,
            coachingPhilosophy = philosophy,
            hourlyRate = hourlyRate
        )
        repository.saveUserProfile(updated)

        val currentUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                val result = repository.firestoreUserRepository.updateCoachDetails(
                    userId = currentUid,
                    certifications = certifications,
                    coachingPhilosophy = philosophy,
                    hourlyRate = hourlyRate
                )
                if (result.isSuccess) {
                    _syncStatus.value = "Profil Coach synchronisé avec Firestore"
                    onComplete(true, "Profil Coach enregistré dans Firestore !")
                } else {
                    onComplete(false, result.exceptionOrNull()?.localizedMessage ?: "Erreur Firestore")
                }
            }
        } else {
            onComplete(true, "Profil Coach enregistré localement !")
        }
    }

    fun selectSportDiscipline(
        sportName: String,
        primaryPosition: String = "",
        onComplete: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        val current = userProfile.value
        val updated = current.copy(
            selectedSport = sportName,
            primaryPosition = if (primaryPosition.isNotBlank()) primaryPosition else current.primaryPosition
        )
        updateProfile(updated)
        _syncStatus.value = "Espò chwazi : $sportName"
        onComplete(true, "Espò chwazi avèk siksè : $sportName")
    }

    fun selectDepartmentCommune(
        department: String,
        commune: String,
        onComplete: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        val current = userProfile.value
        val updated = current.copy(
            selectedDepartment = department,
            selectedCommune = commune,
            city = "$commune, $department, Haïti"
        )
        updateProfile(updated)
        _syncStatus.value = "Lokalizasyon mete ajou : $commune ($department)"
        onComplete(true, "Lokalizasyon anrejistre : $commune ($department)")
    }

    fun updateCommunityUser(
        updatedProfile: UserProfile,
        onComplete: (Boolean, String) -> Unit = { _, _ -> }
    ) {
        repository.updateCommunityUserProfile(updatedProfile)
        val currentUid = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid
        if (currentUid != null) {
            viewModelScope.launch {
                // Coach or Admin saving user profile to Firestore
                val targetId = if (updatedProfile.email.equals(userProfile.value.email, ignoreCase = true)) {
                    currentUid
                } else {
                    "user_${updatedProfile.id}"
                }
                val result = repository.firestoreUserRepository.saveUserProfile(updatedProfile, targetId)
                if (result.isSuccess) {
                    _syncStatus.value = "Profil [${updatedProfile.fullName}] mete ajou nan Firestore"
                    onComplete(true, "Profil [${updatedProfile.fullName}] mete ajou ak siksè !")
                } else {
                    onComplete(true, "Profil [${updatedProfile.fullName}] anrejistre lokalman !")
                }
            }
        } else {
            onComplete(true, "Profil [${updatedProfile.fullName}] anrejistre lokalman !")
        }
    }

    fun switchActiveIdentity(profile: UserProfile) {
        repository.switchActiveProfile(profile)
        _syncStatus.value = "Konekte kòm : ${profile.fullName} (${profile.role})"
    }

    fun initSuperAdminAccount(onComplete: (Boolean, String) -> Unit = { _, _ -> }) {
        val superAdmin = communityUsers.value.find { it.isSuperAdmin }
            ?: UserProfile(
                id = 999L,
                fullName = "Bassian Ysma",
                email = UserProfile.SUPER_ADMIN_EMAIL,
                role = "Super Admin",
                positionOrSpecialty = "Direksyon Jeneral Platfòm Acash Connect",
                bio = "Kont Super Admin ak tout dwa pou jere ak modifye Paran, Atlet, Antrene ak Sipote.",
                city = "Port-au-Prince / Paris"
            )
        switchActiveIdentity(superAdmin)
        onComplete(true, "Konekte kòm SUPER ADMIN (${UserProfile.SUPER_ADMIN_EMAIL}) !")
    }

    fun syncFromFirestore(userId: String) {
        viewModelScope.launch {
            val result = repository.fetchUserProfileFromFirestore(userId)
            if (result.isSuccess && result.getOrNull() != null) {
                _syncStatus.value = "Profil chargé depuis Firestore"
            } else {
                // If profile is not yet on Firestore, seed it with the authenticated user credentials
                val authUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
                if (authUser != null) {
                    val current = userProfile.value
                    val updated = current.copy(
                        fullName = if (!authUser.displayName.isNullOrBlank()) authUser.displayName!! else current.fullName,
                        email = if (!authUser.email.isNullOrBlank()) authUser.email!! else current.email
                    )
                    repository.saveUserProfileToFirestore(updated, userId)
                    _syncStatus.value = "Profil athlète initialisé sur Firestore"
                }
            }
        }
    }

    fun setCategoryFilter(category: PostCategory) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun createPost(content: String, category: PostCategory, highlightTag: String?, bpmStat: String?) {
        repository.addPost(content, category, highlightTag, bpmStat)
    }

    fun toggleLike(postId: Long) {
        repository.toggleLike(postId)
    }

    fun addComment(postId: Long, commentText: String) {
        repository.addComment(postId, commentText)
    }

    fun bookSession(coach: Coach, date: String, time: String, objective: String, notes: String) {
        repository.bookSession(coach, date, time, objective, notes)
    }

    // ==========================================
    // FIBA 3X3 SCOREBOARD & SHOT CLOCK (12s)
    // ==========================================

    fun addPoints(teamA: Boolean, points: Int) {
        val current = _scoreboard.value
        if (current.isGameOver) return

        val newScoreA = if (teamA) current.scoreA + points else current.scoreA
        val newScoreB = if (!teamA) current.scoreB + points else current.scoreB

        // Règle FIBA 3x3 : Première équipe à 21 points gagne immédiatement (Sudden Death)
        if (newScoreA >= 21) {
            triggerVibration()
            _scoreboard.value = current.copy(
                scoreA = newScoreA,
                isGameOver = true,
                isMatchRunning = false,
                isShotClockRunning = false,
                winnerNotice = "VICTOIRE SUDDEN DEATH ! ${current.teamAName} atteint 21 points !"
            )
            stopTimers()
            return
        } else if (newScoreB >= 21) {
            triggerVibration()
            _scoreboard.value = current.copy(
                scoreB = newScoreB,
                isGameOver = true,
                isMatchRunning = false,
                isShotClockRunning = false,
                winnerNotice = "VICTOIRE SUDDEN DEATH ! ${current.teamBName} atteint 21 points !"
            )
            stopTimers()
            return
        }

        _scoreboard.value = current.copy(scoreA = newScoreA, scoreB = newScoreB)
        // Reset 12s Shot Clock après panier
        resetShotClock()
    }

    fun addFoul(teamA: Boolean) {
        val current = _scoreboard.value
        val newFoulsA = if (teamA) current.foulsA + 1 else current.foulsA
        val newFoulsB = if (!teamA) current.foulsB + 1 else current.foulsB

        var alert: String? = null
        if (teamA) {
            if (newFoulsA in 7..9) {
                alert = "⚠️ 7e-9e Faute pour ${current.teamAName} : 2 lancers francs accordés !"
            } else if (newFoulsA >= 10) {
                alert = "🚨 10e FAUTE pour ${current.teamAName} : 2 lancers francs + POSSESSION DE BALLE !"
            }
        } else {
            if (newFoulsB in 7..9) {
                alert = "⚠️ 7e-9e Faute pour ${current.teamBName} : 2 lancers francs accordés !"
            } else if (newFoulsB >= 10) {
                alert = "🚨 10e FAUTE pour ${current.teamBName} : 2 lancers francs + POSSESSION DE BALLE !"
            }
        }

        _scoreboard.value = current.copy(
            foulsA = newFoulsA,
            foulsB = newFoulsB,
            buzzerAlert = alert
        )
    }

    fun toggleMatchTimer() {
        val current = _scoreboard.value
        if (current.isGameOver) return
        val newRunning = !current.isMatchRunning
        _scoreboard.value = current.copy(isMatchRunning = newRunning, isShotClockRunning = newRunning)

        if (newRunning) {
            startMatchTimer()
            startShotClockTimer()
        } else {
            stopTimers()
        }
    }

    fun resetShotClock() {
        _scoreboard.value = _scoreboard.value.copy(shotClockSeconds = 12, buzzerAlert = null)
    }

    fun resetMatch() {
        stopTimers()
        _scoreboard.value = ScoreboardState()
    }

    private fun startMatchTimer() {
        matchTimerJob?.cancel()
        matchTimerJob = viewModelScope.launch {
            while (isActive && _scoreboard.value.isMatchRunning && _scoreboard.value.matchSecondsRemaining > 0) {
                delay(1000L)
                val remaining = _scoreboard.value.matchSecondsRemaining - 1
                if (remaining <= 0) {
                    val sc = _scoreboard.value
                    val winner = when {
                        sc.scoreA > sc.scoreB -> "${sc.teamAName} remporte le match au temps réglementaire !"
                        sc.scoreB > sc.scoreA -> "${sc.teamBName} remporte le match au temps réglementaire !"
                        else -> "ÉGALITÉ ! Prolongation FIBA (Première équipe à 2 points gagne) !"
                    }
                    triggerVibration()
                    _scoreboard.value = sc.copy(
                        matchSecondsRemaining = 0,
                        isMatchRunning = false,
                        isShotClockRunning = false,
                        isGameOver = sc.scoreA != sc.scoreB,
                        winnerNotice = winner
                    )
                    stopTimers()
                } else {
                    _scoreboard.value = _scoreboard.value.copy(matchSecondsRemaining = remaining)
                }
            }
        }
    }

    private fun startShotClockTimer() {
        shotClockJob?.cancel()
        shotClockJob = viewModelScope.launch {
            while (isActive && _scoreboard.value.isShotClockRunning && _scoreboard.value.shotClockSeconds > 0) {
                delay(1000L)
                val remaining = _scoreboard.value.shotClockSeconds - 1
                if (remaining <= 0) {
                    triggerVibration()
                    _scoreboard.value = _scoreboard.value.copy(
                        shotClockSeconds = 0,
                        buzzerAlert = "⏰ BUZZER 12 SECONDES ! Violation de l'horloge de tir FIBA !"
                    )
                } else {
                    _scoreboard.value = _scoreboard.value.copy(shotClockSeconds = remaining)
                }
            }
        }
    }

    private fun stopTimers() {
        matchTimerJob?.cancel()
        matchTimerJob = null
        shotClockJob?.cancel()
        shotClockJob = null
    }

    private fun triggerVibration() {
        try {
            val context = getApplication<Application>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(350)
                }
            }
        } catch (_: Exception) {
            // Ignored if device vibrator is not active in container
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimers()
    }
}
