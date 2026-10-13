package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.Coach
import com.example.data.model.CoachSession
import com.example.data.model.Comment
import com.example.data.model.Post
import com.example.data.model.PostCategory
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AcashRepository(
    context: Context,
    val firestoreUserRepository: FirestoreUserRepository = FirestoreUserRepository(context)
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("acash_connect_prefs", Context.MODE_PRIVATE)

    private val _userProfile = MutableStateFlow(loadUserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    suspend fun saveUserProfileToFirestore(profile: UserProfile, userId: String): Result<Unit> {
        val result = firestoreUserRepository.saveUserProfile(profile, userId)
        if (result.isSuccess) {
            saveUserProfile(profile)
        }
        return result
    }

    suspend fun fetchUserProfileFromFirestore(userId: String): Result<UserProfile?> {
        val result = firestoreUserRepository.getUserProfile(userId)
        result.getOrNull()?.let { remoteProfile ->
            saveUserProfile(remoteProfile)
        }
        return result
    }

    private val _posts = MutableStateFlow(getInitialPosts())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _coaches = MutableStateFlow(getInitialCoaches())
    val coaches: StateFlow<List<Coach>> = _coaches.asStateFlow()

    private val _sessions = MutableStateFlow(getInitialSessions())
    val sessions: StateFlow<List<CoachSession>> = _sessions.asStateFlow()

    private val _communityUsers = MutableStateFlow(getInitialCommunityUsers())
    val communityUsers: StateFlow<List<UserProfile>> = _communityUsers.asStateFlow()

    fun updateCommunityUserProfile(updated: UserProfile) {
        _communityUsers.update { list ->
            list.map { if (it.id == updated.id || it.email.equals(updated.email, ignoreCase = true)) updated else it }
        }
        if (_userProfile.value.id == updated.id || _userProfile.value.email.equals(updated.email, ignoreCase = true)) {
            saveUserProfile(updated)
        }
    }

    fun switchActiveProfile(profile: UserProfile) {
        saveUserProfile(profile)
    }

    private fun loadUserProfile(): UserProfile {
        val fullName = prefs.getString("user_name", "Alexandre Dupont") ?: "Alexandre Dupont"
        val email = prefs.getString("user_email", "alex.3x3@acashconnect.org") ?: "alex.3x3@acashconnect.org"
        val phone = prefs.getString("user_phone", "+33 6 45 78 90 12") ?: "+33 6 45 78 90 12"
        val bloodGroup = prefs.getString("user_blood_group", "O+") ?: "O+"
        val restingBpm = prefs.getInt("user_resting_bpm", 62)
        val maxBpm = prefs.getInt("user_max_bpm", 194)
        val heightCm = prefs.getFloat("user_height_cm", 193f)
        val weightKg = prefs.getFloat("user_weight_kg", 88.5f)
        val gender = prefs.getString("user_gender", "Masculin") ?: "Masculin"
        val role = prefs.getString("user_role", "Athlète") ?: "Athlète"
        val specialty = prefs.getString("user_specialty", "Shooter 2-pts / Meneur") ?: "Shooter 2-pts / Meneur"
        val team = prefs.getString("user_team", "Acash 3x3 Paris Hoops") ?: "Acash 3x3 Paris Hoops"
        val bio = prefs.getString("user_bio", "Athlète Basket 3X3 préparant les qualifications FIBA World Tour & CIO. Focus sur le tir sous 12 secondes et l'intensité cardiovasculaire.") ?: ""
        val city = prefs.getString("user_city", "Paris, France") ?: "Paris, France"
        val fibaRanking = prefs.getInt("user_fiba_ranking", 1420)
        val fieldGoalPercentage = prefs.getFloat("user_fg_pct", 54.2f)
        val twoPointPercentage = prefs.getFloat("user_2pt_pct", 41.5f)
        val reboundsPerGame = prefs.getFloat("user_rpg", 5.8f)
        val assists = prefs.getInt("user_assists", 42)
        val assistsPerGame = prefs.getFloat("user_apg", 3.5f)
        val gamesPlayed = prefs.getInt("user_games_played", 12)
        val pointsPerGame = prefs.getFloat("user_ppg", 7.4f)

        // Postes, compétences & expérience
        val primaryPosition = prefs.getString("user_primary_pos", "Shooter 2-pts / Meneur") ?: "Shooter 2-pts / Meneur"
        val secondaryPosition = prefs.getString("user_secondary_pos", "Ailier Polyvalent") ?: "Ailier Polyvalent"
        val skillLevel = prefs.getString("user_skill_level", "Élite / FIBA World Tour") ?: "Élite / FIBA World Tour"
        val shootingSkill = prefs.getInt("user_shooting_skill", 88)
        val defenseSkill = prefs.getInt("user_defense_skill", 78)
        val playmakingSkill = prefs.getInt("user_playmaking_skill", 84)
        val physicalConditioningSkill = prefs.getInt("user_conditioning_skill", 90)
        val tacticalIqSkill = prefs.getInt("user_tactical_iq_skill", 82)
        val experienceYears = prefs.getInt("user_exp_years", 7)
        val competitionLevel = prefs.getString("user_comp_level", "FIBA 3x3 World Tour & National") ?: "FIBA 3x3 World Tour & National"
        val pastClubsOrTeams = prefs.getString("user_past_clubs", "Paris 3x3 Hoops, Cergy Pontoise Basket, Team Acash") ?: "Paris 3x3 Hoops, Cergy Pontoise Basket, Team Acash"
        val achievements = prefs.getString("user_achievements", "Vainqueur Open Plus FIBA 3x3 Paris 2024, MVP Dunk Contest 2023, Quart de finale Lite Quest") ?: "Vainqueur Open Plus FIBA 3x3 Paris 2024, MVP Dunk Contest 2023, Quart de finale Lite Quest"
        val certifications = prefs.getString("user_certifications", "FIBA 3x3 Coaching License, Brevet Fédéral FFBB, Master STAPS") ?: "FIBA 3x3 Coaching License, Brevet Fédéral FFBB, Master STAPS"
        val coachingPhilosophy = prefs.getString("user_coaching_philosophy", "Jeu ultra-rapide en 12s, tirs primés à fort volume et pressing tout terrain.") ?: "Jeu ultra-rapide en 12s, tirs primés à fort volume et pressing tout terrain."
        val hourlyRate = prefs.getString("user_hourly_rate", "50 € / séance") ?: "50 € / séance"

        return UserProfile(
            fullName = fullName,
            email = email,
            phone = phone,
            bloodGroup = bloodGroup,
            restingBpm = restingBpm,
            maxBpm = maxBpm,
            heightCm = heightCm,
            weightKg = weightKg,
            gender = gender,
            role = role,
            positionOrSpecialty = specialty,
            teamOrClub = team,
            bio = bio,
            city = city,
            fibaRanking = fibaRanking,
            isCurrentUser = true,
            fieldGoalPercentage = fieldGoalPercentage,
            twoPointPercentage = twoPointPercentage,
            reboundsPerGame = reboundsPerGame,
            assists = assists,
            assistsPerGame = assistsPerGame,
            gamesPlayed = gamesPlayed,
            pointsPerGame = pointsPerGame,
            primaryPosition = primaryPosition,
            secondaryPosition = secondaryPosition,
            positions = listOf(primaryPosition, secondaryPosition).distinct(),
            skillLevel = skillLevel,
            shootingSkill = shootingSkill,
            defenseSkill = defenseSkill,
            playmakingSkill = playmakingSkill,
            physicalConditioningSkill = physicalConditioningSkill,
            tacticalIqSkill = tacticalIqSkill,
            experienceYears = experienceYears,
            competitionLevel = competitionLevel,
            pastClubsOrTeams = pastClubsOrTeams,
            achievements = achievements,
            certifications = certifications,
            coachingPhilosophy = coachingPhilosophy,
            hourlyRate = hourlyRate
        )
    }

    fun saveUserProfile(profile: UserProfile) {
        prefs.edit().apply {
            putString("user_name", profile.fullName)
            putString("user_email", profile.email)
            putString("user_phone", profile.phone)
            putString("user_blood_group", profile.bloodGroup)
            putInt("user_resting_bpm", profile.restingBpm)
            putInt("user_max_bpm", profile.maxBpm)
            putFloat("user_height_cm", profile.heightCm)
            putFloat("user_weight_kg", profile.weightKg)
            putString("user_gender", profile.gender)
            putString("user_role", profile.role)
            putString("user_specialty", profile.positionOrSpecialty)
            putString("user_team", profile.teamOrClub)
            putString("user_bio", profile.bio)
            putString("user_city", profile.city)
            putInt("user_fiba_ranking", profile.fibaRanking)
            putFloat("user_fg_pct", profile.fieldGoalPercentage)
            putFloat("user_2pt_pct", profile.twoPointPercentage)
            putFloat("user_rpg", profile.reboundsPerGame)
            putInt("user_assists", profile.assists)
            putFloat("user_apg", profile.assistsPerGame)
            putInt("user_games_played", profile.gamesPlayed)
            putFloat("user_ppg", profile.pointsPerGame)
            putString("user_primary_pos", profile.primaryPosition)
            putString("user_secondary_pos", profile.secondaryPosition)
            putString("user_skill_level", profile.skillLevel)
            putInt("user_shooting_skill", profile.shootingSkill)
            putInt("user_defense_skill", profile.defenseSkill)
            putInt("user_playmaking_skill", profile.playmakingSkill)
            putInt("user_conditioning_skill", profile.physicalConditioningSkill)
            putInt("user_tactical_iq_skill", profile.tacticalIqSkill)
            putInt("user_exp_years", profile.experienceYears)
            putString("user_comp_level", profile.competitionLevel)
            putString("user_past_clubs", profile.pastClubsOrTeams)
            putString("user_achievements", profile.achievements)
            putString("user_certifications", profile.certifications)
            putString("user_coaching_philosophy", profile.coachingPhilosophy)
            putString("user_hourly_rate", profile.hourlyRate)
            apply()
        }
        _userProfile.value = profile
    }

    fun addPost(content: String, category: PostCategory, highlightTag: String?, bpmStat: String?) {
        val current = _userProfile.value
        val newPost = Post(
            id = System.currentTimeMillis(),
            authorName = current.fullName,
            authorRole = current.role,
            authorBadge = if (current.role == "Coach") "Coach Acash Connect" else "Athlète FIBA 3x3",
            timeAgo = "À l'instant",
            content = content,
            category = category,
            likesCount = 0,
            isLiked = false,
            commentsCount = 0,
            sharesCount = 0,
            highlightTag = highlightTag,
            bpmStat = bpmStat,
            comments = emptyList()
        )
        _posts.update { listOf(newPost) + it }
    }

    fun toggleLike(postId: Long) {
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    val newLiked = !post.isLiked
                    val newCount = if (newLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
                    post.copy(isLiked = newLiked, likesCount = newCount)
                } else post
            }
        }
    }

    fun addComment(postId: Long, commentText: String) {
        if (commentText.isBlank()) return
        val current = _userProfile.value
        val newComment = Comment(
            id = System.currentTimeMillis(),
            postId = postId,
            authorName = current.fullName,
            authorRole = current.role,
            text = commentText.trim(),
            timeAgo = "À l'instant"
        )
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    val updatedComments = post.comments + newComment
                    post.copy(comments = updatedComments, commentsCount = updatedComments.size)
                } else post
            }
        }
    }

    fun bookSession(coach: Coach, date: String, time: String, objective: String, notes: String) {
        val current = _userProfile.value
        val newSession = CoachSession(
            id = System.currentTimeMillis(),
            coachName = coach.name,
            athleteName = current.fullName,
            sessionDate = date,
            sessionTime = time,
            objective = objective,
            status = "Confirmé",
            notes = notes
        )
        _sessions.update { listOf(newSession) + it }
    }

    private fun getInitialPosts(): List<Post> {
        return listOf(
            Post(
                id = 101L,
                authorName = "Coach Marcus Vance",
                authorRole = "Coach",
                authorBadge = "FIBA Level 3 & Performance CIO",
                timeAgo = "Il y a 25 min",
                content = "🏀 RÈGLE D'OR FIBA 3X3 : L'horloge de 12 secondes ne pardonne aucune hésitation. Sur notre séance matinale, nous avons travaillé le déclenchement du tir à 2 points en moins de 3,5s après écran. Les athlètes ont maintenu un BPM moyen de 178 bpm. Préparez vos jambes !",
                category = PostCategory.ENTRAINEMENT,
                likesCount = 42,
                isLiked = false,
                commentsCount = 3,
                sharesCount = 8,
                highlightTag = "Shot Clock 12s",
                bpmStat = "Zone cible : 175-188 BPM",
                comments = listOf(
                    Comment(1L, 101L, "David K.", "Athlète", "Cet exercice brûle les poumons mais le tir sort tellement plus fluide !", "Il y a 15 min"),
                    Comment(2L, 101L, "Sarah T.", "Athlète", "Coach, est-ce qu'on refait le circuit cardio demain à 18h ?", "Il y a 10 min"),
                    Comment(3L, 101L, "Coach Marcus Vance", "Coach", "@Sarah oui, circuit haute intensité 10x 1 minute !", "Il y a 4 min")
                )
            ),
            Post(
                id = 102L,
                authorName = "Sarah Traoré",
                authorRole = "Athlète",
                authorBadge = "Sélection CIO 3x3 Paris",
                timeAgo = "Il y a 2 h",
                content = "Victoire 21 - 18 en finale du tournoi 3x3 Masters ! Le tir à 2 points avec la planche au buzzer a fait exploser le score à la 8e minute. Check de ma fréquence cardiaque : 192 BPM au pic du match. Merci à mon coach pour la prépa physique personnalisée sur Acash Connect !",
                category = PostCategory.MATCH_SCORE,
                likesCount = 89,
                isLiked = true,
                commentsCount = 2,
                sharesCount = 14,
                highlightTag = "Victoire 21-18 FIBA",
                bpmStat = "Pic BPM : 192 | FC Repos : 58",
                comments = listOf(
                    Comment(4L, 102L, "Alexandre Dupont", "Athlète", "Quel tir incroyable Sarah ! Bravo pour le titre !", "Il y a 1 h"),
                    Comment(5L, 102L, "Coach Alexis", "Coach", "Superbe gestion des fautes d'équipe dans le money-time !", "Il y a 45 min")
                )
            ),
            Post(
                id = 103L,
                authorName = "Coach Alexis Morel",
                authorRole = "Coach",
                authorBadge = "Spécialiste Tactique NBA & 3x3",
                timeAgo = "Il y a 4 h",
                content = "📋 ANALYSE TACTIQUE FIBA vs NBA : En 3x3, chaque tir à 2 points vaut 100% de plus qu'un tir à 1 point (contrairement au 5x5 où le 3-pts ne vaut que 50% de plus). Mathématiquement, interdire le tir à 2 points adverse en switchant systématiquement est la clé n°1 de la victoire olympique.",
                category = PostCategory.TACTIQUE_FIBA,
                likesCount = 57,
                isLiked = false,
                commentsCount = 1,
                sharesCount = 19,
                highlightTag = "Défense Switch All",
                bpmStat = "Intensité Défensive : Maximale",
                comments = listOf(
                    Comment(6L, 103L, "Malik B.", "Athlète", "Très clair coach, c'est pour ça qu'on évite les aides profondes sous l'arceau.", "Il y a 3 h")
                )
            ),
            Post(
                id = 104L,
                authorName = "Dr. Julien Bertin",
                authorRole = "Coach",
                authorBadge = "Médecin du Sport & Cardiologue Fédéral",
                timeAgo = "Il y a 6 h",
                content = "❤️ BILAN SANTÉ & BIOMÉTRIE ATHLÈTES : N'oubliez pas de mettre à jour votre profil Acash Connect (Groupe sanguin, BPM repos, Hauteur et Poids). Le 3x3 est l'un des sports les plus intenses en filière anaérobie lactique. Surveillez votre variabilité cardiaque et vos temps de récupération entre les matchs.",
                category = PostCategory.SANTE_BPM,
                likesCount = 64,
                isLiked = false,
                commentsCount = 0,
                sharesCount = 11,
                highlightTag = "Passeport Médical FIBA",
                bpmStat = "Zone Sécurité Cardiaque",
                comments = emptyList()
            )
        )
    }

    private fun getInitialCoaches(): List<Coach> {
        return listOf(
            Coach(
                id = 201L,
                name = "Coach Marcus Vance",
                title = "Entraîneur Principal FIBA 3x3 World Tour",
                certification = "FIBA Level 3 & CIO Performance",
                experienceYears = 12,
                specialty = "Tactique 3x3 & Vitesse d'Exécution 12s",
                rating = 4.95f,
                reviewsCount = 48,
                hourlyRate = "45 € / séance",
                bio = "Coach certifié ayant entraîné des équipes nationales 3x3. Spécialiste des systèmes de jeu rapides, pick & roll court et adaptation aux règles d'arbitrage FIBA.",
                availableSlots = listOf("Demain 10:00", "Demain 16:30", "Jeudi 14:00", "Vendredi 18:00")
            ),
            Coach(
                id = 202L,
                name = "Coach Alexis Morel",
                title = "Consultant Développement NBA & 3x3",
                certification = "NBA Skills & FIBA Certified",
                experienceYears = 9,
                specialty = "Tir à 2-Points & Dribble Décisif 1c1",
                rating = 4.88f,
                reviewsCount = 34,
                hourlyRate = "50 € / séance",
                bio = "Ancien joueur professionnel et spécialiste du tir extérieur sous haute intensité cardio. Travail sur la biomécanique du tir sous fatigue extrême.",
                availableSlots = listOf("Mercredi 11:00", "Jeudi 17:00", "Samedi 09:30")
            ),
            Coach(
                id = 203L,
                name = "Dr. Hélène Castan",
                title = "Préparatrice Physique & Monitrice BPM",
                certification = "Master STAPS & Performance Olympique CIO",
                experienceYears = 8,
                specialty = "Endurance Anaérobie & Récupération Cardiaque",
                rating = 4.92f,
                reviewsCount = 29,
                hourlyRate = "40 € / séance",
                bio = "Optimisation du rapport poids/puissance, explosivité verticale et contrôle de la fréquence cardiaque lors des transitions de 12 secondes.",
                availableSlots = listOf("Demain 09:00", "Mercredi 15:00", "Vendredi 11:30")
            )
        )
    }

    private fun getInitialSessions(): List<CoachSession> {
        return listOf(
            CoachSession(
                id = 301L,
                coachName = "Coach Marcus Vance",
                athleteName = "Alexandre Dupont",
                sessionDate = "Mercredi 14 Oct.",
                sessionTime = "16:30",
                objective = "Gestion du Shot Clock 12s & Tir contesté à 2 pts",
                status = "Confirmé",
                notes = "Venir avec moniteur de fréquence cardiaque et tenue extérieure 3x3."
            ),
            CoachSession(
                id = 302L,
                coachName = "Dr. Hélène Castan",
                athleteName = "Alexandre Dupont",
                sessionDate = "Vendredi 16 Oct.",
                sessionTime = "11:30",
                objective = "Test d'effort VMA & Analyse BPM seuil 3x3",
                status = "En attente",
                notes = "Mesure du BPM repos et test navette haute intensité."
            )
        )
    }

    private fun getInitialCommunityUsers(): List<UserProfile> {
        return listOf(
            // 1. KONT SUPER ADMIN (bassianysma@gmail.com)
            UserProfile(
                id = 999L,
                fullName = "Bassian Ysma",
                email = UserProfile.SUPER_ADMIN_EMAIL,
                phone = "+509 37 88 99 00",
                bloodGroup = "O+",
                restingBpm = 58,
                maxBpm = 188,
                heightCm = 186f,
                weightKg = 82f,
                gender = "Masculin",
                role = "Super Admin",
                positionOrSpecialty = "Direksyon Jeneral & Sipèvizyon Platfòm",
                teamOrClub = "Direksyon Acash Connect",
                bio = "Kont Super Admin ak tout dwa pou modifye tout enfòmasyon Paran, Atlèt, Antrenè ak Sipòtè nan tout kominote a.",
                city = "Port-au-Prince / Paris",
                fibaRanking = 2500,
                isCurrentUser = false,
                notes = "Aksè total Super Admin aktif."
            ),

            // 2. ATLET (Athlètes)
            UserProfile(
                id = 1L,
                fullName = "Alexandre Dupont",
                email = "alex.3x3@acashconnect.org",
                phone = "+33 6 45 78 90 12",
                bloodGroup = "O+",
                restingBpm = 62,
                maxBpm = 194,
                heightCm = 193f,
                weightKg = 88.5f,
                gender = "Masculin",
                role = "Athlète",
                primaryPosition = "Shooter 2-pts / Meneur",
                secondaryPosition = "Ailier Polyvalent",
                positions = listOf("Shooter 2-pts", "Meneur", "Ailier"),
                skillLevel = "Élite / FIBA World Tour",
                shootingSkill = 88,
                defenseSkill = 78,
                playmakingSkill = 84,
                physicalConditioningSkill = 90,
                tacticalIqSkill = 82,
                experienceYears = 7,
                competitionLevel = "FIBA 3x3 World Tour & National",
                pastClubsOrTeams = "Paris 3x3 Hoops, Cergy Pontoise Basket, Team Acash",
                achievements = "Vainqueur Open Plus FIBA 3x3 Paris 2024, MVP Dunk Contest 2023",
                teamOrClub = "Acash 3x3 Paris Hoops",
                bio = "Athlète Basket 3X3 préparant les qualifications FIBA World Tour & CIO. Focus sur le tir sous 12 secondes et l'intensité cardiovasculaire.",
                city = "Paris, France",
                fibaRanking = 1420,
                isCurrentUser = true,
                fieldGoalPercentage = 54.2f,
                twoPointPercentage = 41.5f,
                reboundsPerGame = 5.8f,
                assists = 42,
                assistsPerGame = 3.5f,
                gamesPlayed = 12,
                pointsPerGame = 7.4f
            ),
            UserProfile(
                id = 2L,
                fullName = "Tariq Owens",
                email = "tariq.owens@acashconnect.org",
                phone = "+33 6 11 22 33 44",
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
                shootingSkill = 74,
                defenseSkill = 94,
                playmakingSkill = 68,
                physicalConditioningSkill = 96,
                tacticalIqSkill = 86,
                experienceYears = 9,
                competitionLevel = "FIBA 3x3 World Tour Pro",
                pastClubsOrTeams = "Monaco 3x3, Paris Elite",
                achievements = "Finaliste World Tour Utsunomiya, 3x Champion National 3x3",
                teamOrClub = "Acash 3x3 Paris Hoops",
                bio = "Pivot dominant au rebond offensif et protection de cercle.",
                city = "Lyon, France",
                fibaRanking = 1890,
                isCurrentUser = false,
                fieldGoalPercentage = 62.0f,
                twoPointPercentage = 28.5f,
                reboundsPerGame = 8.4f,
                assists = 18,
                assistsPerGame = 1.5f,
                gamesPlayed = 12,
                pointsPerGame = 8.1f
            ),
            UserProfile(
                id = 3L,
                fullName = "Maya Lin",
                email = "maya.lin@acashconnect.org",
                phone = "+33 7 55 44 33 22",
                bloodGroup = "B+",
                restingBpm = 54,
                maxBpm = 196,
                heightCm = 176f,
                weightKg = 64f,
                gender = "Féminin",
                role = "Athlète",
                primaryPosition = "Meneuse 2-pts",
                secondaryPosition = "Playmaker Rapide",
                positions = listOf("Meneuse", "Sniper 2-pts"),
                skillLevel = "Élite / FIBA World Tour",
                shootingSkill = 92,
                defenseSkill = 80,
                playmakingSkill = 91,
                physicalConditioningSkill = 88,
                tacticalIqSkill = 90,
                experienceYears = 6,
                competitionLevel = "FIBA 3x3 Women's Series",
                pastClubsOrTeams = "Lyon Asvel 3x3",
                achievements = "Médaille d'Argent Coupe d'Europe 3x3",
                teamOrClub = "Acash 3x3 Paris Hoops",
                bio = "Meneuse explosive avec tir fiable derrière l'arc 6m75.",
                city = "Bordeaux, France",
                fibaRanking = 1650,
                isCurrentUser = false,
                fieldGoalPercentage = 51.5f,
                twoPointPercentage = 44.0f,
                reboundsPerGame = 3.2f,
                assists = 55,
                assistsPerGame = 4.6f,
                gamesPlayed = 12,
                pointsPerGame = 6.8f
            ),

            // 3. ANTRENE (Coaches)
            UserProfile(
                id = 101L,
                fullName = "Coach Marcus Vance",
                email = "marcus.vance@acashconnect.org",
                phone = "+33 6 12 34 56 78",
                bloodGroup = "A+",
                restingBpm = 58,
                maxBpm = 182,
                heightCm = 196f,
                weightKg = 92f,
                gender = "Masculin",
                role = "Coach",
                primaryPosition = "Head Coach FIBA 3x3",
                secondaryPosition = "Stratège Shot Clock 12s",
                positions = listOf("Head Coach", "Stratégie"),
                skillLevel = "Professionnel / International",
                shootingSkill = 88,
                defenseSkill = 90,
                playmakingSkill = 86,
                physicalConditioningSkill = 82,
                tacticalIqSkill = 96,
                experienceYears = 12,
                competitionLevel = "Ex-Pro Pro A & Coach FIBA 3x3",
                pastClubsOrTeams = "Paris Basketball, Gravelines",
                achievements = "Coach Certifié FIBA 3x3 Master, Champion de France 3x3",
                certifications = "Licence Coach FIBA 3x3 Level 2, Brevet d'État 2ème Degré",
                coachingPhilosophy = "Pression défensive tout-terrain, transition sous 4 secondes et tir à fort volume.",
                hourlyRate = "50 € / séance",
                teamOrClub = "Staff Acash Connect",
                bio = "Ancien joueur professionnel et spécialiste du tir extérieur sous haute intensité cardio.",
                city = "Paris, France",
                isCurrentUser = false
            ),
            UserProfile(
                id = 102L,
                fullName = "Sarah Lefevre",
                email = "sarah.lefevre@acashconnect.org",
                phone = "+33 6 99 88 77 66",
                bloodGroup = "O-",
                restingBpm = 56,
                maxBpm = 180,
                heightCm = 178f,
                weightKg = 68f,
                gender = "Féminin",
                role = "Coach",
                primaryPosition = "Entraîneur Principal 3x3",
                secondaryPosition = "Spécialiste Tir & Shot Clock",
                positions = listOf("Head Coach", "Tir"),
                skillLevel = "Professionnel / International",
                shootingSkill = 92,
                defenseSkill = 86,
                playmakingSkill = 90,
                physicalConditioningSkill = 80,
                tacticalIqSkill = 98,
                experienceYears = 14,
                competitionLevel = "Équipe de France 3x3 & FIBA Instructor",
                pastClubsOrTeams = "FFBB Pôle France, Bourges Basket",
                achievements = "Médaille d'Or Coupe d'Europe 3x3, Coach de l'Année 2022",
                certifications = "FIBA 3x3 Certified Instructor, Brevet d'État 2ème Degré",
                coachingPhilosophy = "Gestion chirurgicale du Shot Clock 12s et spacing parfait.",
                hourlyRate = "65 € / séance",
                teamOrClub = "Staff Acash Connect",
                bio = "Instructrice officielle FIBA et formatrice des jeunes talents 3x3.",
                city = "Paris, France",
                isCurrentUser = false
            ),

            // 4. PARAN (Parents)
            UserProfile(
                id = 201L,
                fullName = "Pierre Dupont",
                email = "pierre.dupont@acashconnect.org",
                phone = "+33 6 77 88 99 00",
                bloodGroup = "O+",
                restingBpm = 68,
                maxBpm = 170,
                heightCm = 182f,
                weightKg = 84f,
                gender = "Masculin",
                role = "Parent",
                positionOrSpecialty = "Paran Alexandre Dupont - Sipò Fanmi",
                teamOrClub = "Asosyasyon Paran Acash",
                bio = "Paran akonpagnatè nan tout tounwa 3x3 nasyonal ak entènasyonal. Sipòte devlopman atletik ak etid.",
                city = "Paris, France",
                isCurrentUser = false,
                notes = "Suivi médical et nutritionnel de l'athlète à jour."
            ),
            UserProfile(
                id = 202L,
                fullName = "Marie Owens",
                email = "marie.owens@acashconnect.org",
                phone = "+33 6 44 33 22 11",
                bloodGroup = "A+",
                restingBpm = 65,
                maxBpm = 175,
                heightCm = 174f,
                weightKg = 67f,
                gender = "Féminin",
                role = "Parent",
                positionOrSpecialty = "Paran Tariq Owens - Responsab Kominikasyon Fanmi",
                teamOrClub = "Asosyasyon Paran Acash",
                bio = "Responsab kowòdinasyon paran pou vwayaj ak lojistik tounwa FIBA World Tour.",
                city = "Lyon, France",
                isCurrentUser = false,
                notes = "Dokiman vwayaj ak asirans valide."
            ),

            // 5. SIPOTE (Supporters)
            UserProfile(
                id = 301L,
                fullName = "Jean-Luc Bastien",
                email = "jeanluc.bastien@acashconnect.org",
                phone = "+33 6 55 66 77 88",
                bloodGroup = "AB+",
                restingBpm = 70,
                maxBpm = 175,
                heightCm = 180f,
                weightKg = 80f,
                gender = "Masculin",
                role = "Supporter",
                positionOrSpecialty = "Prezidan Klib Sipòtè Paris Hoops",
                teamOrClub = "Klib Sipòtè Acash Connect",
                bio = "Fanatik nimewo 1 Basket 3X3 depi premye edisyon FIBA World Tour. Animatè tribin ak kreyatè kontni.",
                city = "Paris, France",
                isCurrentUser = false,
                notes = "Abonnement VIP match 3x3 actif."
            ),
            UserProfile(
                id = 302L,
                fullName = "Clarisse Noel",
                email = "clarisse.noel@acashconnect.org",
                phone = "+509 36 22 11 00",
                bloodGroup = "B+",
                restingBpm = 66,
                maxBpm = 178,
                heightCm = 168f,
                weightKg = 60f,
                gender = "Féminin",
                role = "Supporter",
                positionOrSpecialty = "Sipòtè Entènasyonal & Fan Acash",
                teamOrClub = "Klib Sipòtè Acash Connect",
                bio = "Sipòtè pasyone ki swiv tout match ak pèfòmans atlet Ayisyen ak entènasyonal nan 3x3.",
                city = "Port-au-Prince, Haïti",
                isCurrentUser = false,
                notes = "Manm kominote sipòtè lòt bò dlo."
            ),

            // 6. DYASPORA AMBASSADOR (Josué Bien Aimé - Canada)
            UserProfile(
                id = 401L,
                fullName = "Josué Bien Aimé",
                email = "josue.bienaime@acashconnect.org",
                phone = "+1 514 889 0012",
                bloodGroup = "O+",
                restingBpm = 55,
                maxBpm = 192,
                heightCm = 188f,
                weightKg = 84f,
                gender = "Masculin",
                role = "Athlète",
                selectedSport = "Basket 3x3",
                secondarySports = listOf("Football", "Athlétisme"),
                selectedDepartment = "Dyaspora Canada",
                selectedCommune = "Montréal",
                positionOrSpecialty = "Lidè Dyaspora Canada • Meneur / Ailier",
                teamOrClub = "Montreal Hoops & ACASH Diaspora",
                bio = "Lidè Kowòdinasyon Dyaspora ACASH nan Kanada. Angaje nan kreye pon ant talan ayisyen nan monn lan ak jèn nan 10 depatman Ayiti.",
                city = "Montréal, Québec, Canada",
                isCurrentUser = false,
                fibaRanking = 1380,
                notes = "Anbasadè Ofisyèl Dyaspora ACASH Kanada."
            )
        )
    }
}
