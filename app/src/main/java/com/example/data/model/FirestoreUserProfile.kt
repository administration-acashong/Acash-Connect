package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class FirestoreUserProfile(
    @DocumentId val id: String = "",
    val userId: String = "",
    val fullName: String = "Alexandre Dupont",
    val email: String = "alex.3x3@acashconnect.org",
    val phone: String = "+33 6 45 78 90 12",
    val bloodGroup: String = "O+",
    val restingBpm: Long = 62L,
    val maxBpm: Long = 194L,
    val heightCm: Double = 193.0,
    val weightKg: Double = 88.5,
    val gender: String = "Masculin",
    val role: String = "Athlète",
    val positionOrSpecialty: String = "Shooter 2-pts / Meneur",
    val teamOrClub: String = "Acash 3x3 Paris Hoops",
    val affiliatedSchool: String = "",
    val bio: String = "",
    val city: String = "Paris, France",
    val fibaRanking: Long = 1420L,
    val selectedSport: String = "Basketball 3x3",
    val secondarySports: List<String> = listOf("Football", "Athlétisme"),
    val selectedDepartment: String = "Lwès",
    val selectedCommune: String = "Pòtoprens",

    // 3X3 Basketball Performance Metrics
    val fieldGoalPercentage: Double = 54.2,
    val twoPointPercentage: Double = 41.5,
    val reboundsPerGame: Double = 5.8,
    val assists: Long = 42L,
    val assistsPerGame: Double = 3.5,
    val gamesPlayed: Long = 12L,
    val pointsPerGame: Double = 7.4,

    // Positions & Specialties
    val primaryPosition: String = "Shooter 2-pts / Meneur",
    val secondaryPosition: String = "Ailier Polyvalent",
    val positions: List<String> = listOf("Shooter 2-pts", "Meneur", "Ailier"),

    // Skill Levels
    val skillLevel: String = "Élite / FIBA World Tour",
    val shootingSkill: Long = 88L,
    val defenseSkill: Long = 78L,
    val playmakingSkill: Long = 84L,
    val physicalConditioningSkill: Long = 90L,
    val tacticalIqSkill: Long = 82L,

    // Basketball Experience
    val experienceYears: Long = 7L,
    val competitionLevel: String = "FIBA 3x3 World Tour & National",
    val pastClubsOrTeams: String = "Paris 3x3 Hoops, Cergy Pontoise Basket, Team Acash",
    val achievements: String = "Vainqueur Open Plus FIBA 3x3 Paris 2024, MVP Dunk Contest 2023, Quart de finale Lite Quest",

    // Coach Specific Fields
    val certifications: String = "FIBA 3x3 Coaching License, Brevet Fédéral FFBB, Master STAPS",
    val coachingPhilosophy: String = "Jeu ultra-rapide en 12s, tirs primés à fort volume et pressing tout terrain.",
    val hourlyRate: String = "50 € / séance",
    val notes: String = "",

    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
) {
    fun toUserProfile(): UserProfile {
        return UserProfile(
            id = 1L,
            fullName = fullName,
            email = email,
            phone = phone,
            bloodGroup = bloodGroup,
            restingBpm = restingBpm.toInt(),
            maxBpm = maxBpm.toInt(),
            heightCm = heightCm.toFloat(),
            weightKg = weightKg.toFloat(),
            gender = gender,
            role = role,
            positionOrSpecialty = positionOrSpecialty,
            teamOrClub = teamOrClub,
            affiliatedSchool = affiliatedSchool,
            bio = bio,
            city = city,
            fibaRanking = fibaRanking.toInt(),
            isCurrentUser = true,
            selectedSport = selectedSport,
            secondarySports = secondarySports,
            selectedDepartment = selectedDepartment,
            selectedCommune = selectedCommune,
            fieldGoalPercentage = fieldGoalPercentage.toFloat(),
            twoPointPercentage = twoPointPercentage.toFloat(),
            reboundsPerGame = reboundsPerGame.toFloat(),
            assists = assists.toInt(),
            assistsPerGame = assistsPerGame.toFloat(),
            gamesPlayed = gamesPlayed.toInt(),
            pointsPerGame = pointsPerGame.toFloat(),
            primaryPosition = primaryPosition,
            secondaryPosition = secondaryPosition,
            positions = positions,
            skillLevel = skillLevel,
            shootingSkill = shootingSkill.toInt(),
            defenseSkill = defenseSkill.toInt(),
            playmakingSkill = playmakingSkill.toInt(),
            physicalConditioningSkill = physicalConditioningSkill.toInt(),
            tacticalIqSkill = tacticalIqSkill.toInt(),
            experienceYears = experienceYears.toInt(),
            competitionLevel = competitionLevel,
            pastClubsOrTeams = pastClubsOrTeams,
            achievements = achievements,
            certifications = certifications,
            coachingPhilosophy = coachingPhilosophy,
            hourlyRate = hourlyRate,
            notes = notes
        )
    }

    companion object {
        fun fromUserProfile(profile: UserProfile, userId: String): FirestoreUserProfile {
            return FirestoreUserProfile(
                id = userId,
                userId = userId,
                fullName = profile.fullName,
                email = profile.email,
                phone = profile.phone,
                bloodGroup = profile.bloodGroup,
                restingBpm = profile.restingBpm.toLong(),
                maxBpm = profile.maxBpm.toLong(),
                heightCm = profile.heightCm.toDouble(),
                weightKg = profile.weightKg.toDouble(),
                gender = profile.gender,
                role = profile.role,
                positionOrSpecialty = profile.positionOrSpecialty,
                teamOrClub = profile.teamOrClub,
                affiliatedSchool = profile.affiliatedSchool,
                bio = profile.bio,
                city = profile.city,
                fibaRanking = profile.fibaRanking.toLong(),
                selectedSport = profile.selectedSport,
                secondarySports = profile.secondarySports,
                selectedDepartment = profile.selectedDepartment,
                selectedCommune = profile.selectedCommune,
                fieldGoalPercentage = profile.fieldGoalPercentage.toDouble(),
                twoPointPercentage = profile.twoPointPercentage.toDouble(),
                reboundsPerGame = profile.reboundsPerGame.toDouble(),
                assists = profile.assists.toLong(),
                assistsPerGame = profile.assistsPerGame.toDouble(),
                gamesPlayed = profile.gamesPlayed.toLong(),
                pointsPerGame = profile.pointsPerGame.toDouble(),
                primaryPosition = profile.primaryPosition,
                secondaryPosition = profile.secondaryPosition,
                positions = profile.positions,
                skillLevel = profile.skillLevel,
                shootingSkill = profile.shootingSkill.toLong(),
                defenseSkill = profile.defenseSkill.toLong(),
                playmakingSkill = profile.playmakingSkill.toLong(),
                physicalConditioningSkill = profile.physicalConditioningSkill.toLong(),
                tacticalIqSkill = profile.tacticalIqSkill.toLong(),
                experienceYears = profile.experienceYears.toLong(),
                competitionLevel = profile.competitionLevel,
                pastClubsOrTeams = profile.pastClubsOrTeams,
                achievements = profile.achievements,
                certifications = profile.certifications,
                coachingPhilosophy = profile.coachingPhilosophy,
                hourlyRate = profile.hourlyRate,
                notes = profile.notes,
                createdAt = null,
                updatedAt = null
            )
        }
    }
}
