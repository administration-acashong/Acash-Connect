package com.example.data.model

/**
 * Rôles disponibles pour les utilisateurs d'Acash Connect
 * - ATLET (Athlète)
 * - ANTRENE (Coach / Antrenè)
 * - PARAN (Parent)
 * - SIPOTE (Supporter / Sipòtè)
 * - SUPER ADMIN (Administrateur principal)
 */
enum class ProfileRole(val displayName: String, val kreylName: String) {
    ATHLETE("Athlète", "Atlèt"),
    COACH("Coach", "Antrenè"),
    PARENT("Parent", "Paran"),
    SUPPORTER("Supporter", "Sipòtè"),
    SUPER_ADMIN("Super Admin", "Super Admin");

    companion object {
        fun fromString(value: String): ProfileRole {
            return when {
                value.equals("Super Admin", ignoreCase = true) || value.equals("Admin", ignoreCase = true) -> SUPER_ADMIN
                value.equals("Coach", ignoreCase = true) || value.equals("Antrenè", ignoreCase = true) || value.equals("Antrene", ignoreCase = true) -> COACH
                value.equals("Parent", ignoreCase = true) || value.equals("Paran", ignoreCase = true) -> PARENT
                value.equals("Supporter", ignoreCase = true) || value.equals("Sipòtè", ignoreCase = true) || value.equals("Sipote", ignoreCase = true) -> SUPPORTER
                else -> ATHLETE
            }
        }
    }
}

/**
 * Modèle de données complet pour le profil d'un utilisateur d'Acash Connect
 */
data class UserProfile(
    val id: Long = 1L,
    val fullName: String = "Alexandre Dupont",
    val email: String = "alex.3x3@acashconnect.org",
    val phone: String = "+33 6 45 78 90 12",
    val bloodGroup: String = "O+", // A+, A-, B+, B-, AB+, AB-, O+, O-
    val restingBpm: Int = 62, // Battements Par Minute au repos
    val maxBpm: Int = 194, // BPM Max atteint à l'effort 3x3
    val heightCm: Float = 193f, // Hauteur en cm
    val weightKg: Float = 88.5f, // Poids en kg
    val gender: String = "Masculin", // Masculin / Féminin / Autre
    val role: String = "Athlète", // "Athlète", "Coach", "Parent", "Supporter", "Super Admin"
    val positionOrSpecialty: String = "Shooter 2-pts / Meneur",
    val teamOrClub: String = "Acash 3x3 Paris Hoops",
    val affiliatedSchool: String = "", // Écoles Fondamental Affilée (6 établissements)
    val bio: String = "Athlète Basket 3X3 préparant les qualifications FIBA World Tour & CIO. Focus sur le tir sous 12 secondes et l'intensité cardiovasculaire.",
    val city: String = "Paris, France",
    val fibaRanking: Int = 1420,
    val isCurrentUser: Boolean = true,
    val selectedSport: String = "Basketball 3x3",
    val secondarySports: List<String> = listOf("Football", "Athlétisme"),
    val selectedDepartment: String = "Lwès",
    val selectedCommune: String = "Pòtoprens",

    // Métriques de performance 3X3 Basketball
    val fieldGoalPercentage: Float = 54.2f, // FG% (Pourcentage global de réussite)
    val twoPointPercentage: Float = 41.5f, // 2-Pts % (Tirs derrière l'arc 6m75)
    val reboundsPerGame: Float = 5.8f, // RPG (Rebonds par match)
    val assists: Int = 42, // Passes décisives totales
    val assistsPerGame: Float = 3.5f, // APG (Passes par match)
    val gamesPlayed: Int = 12, // Matchs officiels joués
    val pointsPerGame: Float = 7.4f, // PPG (Points par match sur 21 max)

    // Postes & Spécialisations Basketball
    val primaryPosition: String = "Shooter 2-pts / Meneur",
    val secondaryPosition: String = "Ailier Polyvalent",
    val positions: List<String> = listOf("Shooter 2-pts", "Meneur", "Ailier"),

    // Niveaux de compétences (Skill Levels)
    val skillLevel: String = "Élite / FIBA World Tour",
    val shootingSkill: Int = 88,
    val defenseSkill: Int = 78,
    val playmakingSkill: Int = 84,
    val physicalConditioningSkill: Int = 90,
    val tacticalIqSkill: Int = 82,

    // Expérience Basketball
    val experienceYears: Int = 7,
    val competitionLevel: String = "FIBA 3x3 World Tour & National",
    val pastClubsOrTeams: String = "Paris 3x3 Hoops, Cergy Pontoise Basket, Team Acash",
    val achievements: String = "Vainqueur Open Plus FIBA 3x3 Paris 2024, MVP Dunk Contest 2023, Quart de finale Lite Quest",

    // Champs spécifiques Coach
    val certifications: String = "FIBA 3x3 Coaching License, Brevet Fédéral FFBB, Master STAPS",
    val coachingPhilosophy: String = "Jeu ultra-rapide en 12s, tirs primés à fort volume et pressing tout terrain.",
    val hourlyRate: String = "50 € / séance",

    // Notes de coaching ou administration
    val notes: String = ""
) {
    companion object {
        const val SUPER_ADMIN_EMAIL = "bassianysma@gmail.com"
        const val SECONDARY_ADMIN_EMAIL = "administration@acashong.com"
        const val SHARE_APP_URL = "https://ais-pre-3pr4ayrhzuhl6lj7eeqcpe-304588352517.us-east1.run.app"
    }

    val isSuperAdmin: Boolean
        get() = email.equals(SUPER_ADMIN_EMAIL, ignoreCase = true) ||
                email.equals(SECONDARY_ADMIN_EMAIL, ignoreCase = true) ||
                role.equals("Super Admin", ignoreCase = true) ||
                role.equals("Admin", ignoreCase = true)

    val isCoach: Boolean
        get() = role.equals("Coach", ignoreCase = true) ||
                role.equals("Antrenè", ignoreCase = true) ||
                role.equals("Antrene", ignoreCase = true)

    val isParent: Boolean
        get() = role.equals("Parent", ignoreCase = true) ||
                role.equals("Paran", ignoreCase = true)

    val isSupporter: Boolean
        get() = role.equals("Supporter", ignoreCase = true) ||
                role.equals("Sipòtè", ignoreCase = true) ||
                role.equals("Sipote", ignoreCase = true)

    val isAthlete: Boolean
        get() = role.equals("Athlète", ignoreCase = true) ||
                role.equals("Atlèt", ignoreCase = true) ||
                role.equals("Atlet", ignoreCase = true) ||
                (!isCoach && !isParent && !isSupporter && !isSuperAdmin)

    /**
     * Vérifie si cet utilisateur a le droit de modifier le profil cible :
     * 1. Super Admin peut modifier TOUT LE MONDE (Paran, Atlet, Antrene, Sipote)
     * 2. Coach peut modifier les ATLÈTS
     * 3. Chaque utilisateur peut modifier son propre profil
     */
    fun canModifyProfile(target: UserProfile): Boolean {
        if (isSuperAdmin) return true
        if (isCoach && target.isAthlete) return true
        if (email.equals(target.email, ignoreCase = true) || id == target.id) return true
        return false
    }

    // Calcul de la note globale moyenne de compétence (OVR)
    val overallRating: Int
        get() = ((shootingSkill + defenseSkill + playmakingSkill + physicalConditioningSkill + tacticalIqSkill) / 5)

    // Calcul IMC / BMI
    val bmi: Float
        get() {
            val heightM = heightCm / 100f
            return if (heightM > 0) weightKg / (heightM * heightM) else 0f
        }

    val bmiCategory: String
        get() = when {
            bmi < 18.5f -> "Poids insuffisant"
            bmi in 18.5f..24.9f -> "Athlète normal / Optimal"
            bmi in 25.0f..29.9f -> "Masse athlétique / Puissance"
            else -> "Masse élevée"
        }

    // Zone d'entraînement 3X3 selon le BPM (FIBA 3x3 = 85% à 95% FC max)
    val targetZone3x3Bpm: String
        get() = "${(maxBpm * 0.85).toInt()} - ${(maxBpm * 0.95).toInt()} BPM"
}
