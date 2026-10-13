package com.example.data.model

enum class PostCategory(val label: String, val icon: String) {
    ALL("Tous", "🔥"),
    ENTRAINEMENT("Entraînement 3x3", "⚡"),
    TACTIQUE_FIBA("Tactique FIBA", "📋"),
    MATCH_SCORE("Match & Tournoi", "🏆"),
    SANTE_BPM("Santé & Cardio", "❤️"),
    COACH_TIP("Conseils Coach", "🎯")
}

data class Post(
    val id: Long,
    val authorName: String,
    val authorRole: String, // "Athlète" ou "Coach"
    val authorBadge: String, // "FIBA Certified Coach", "Athlète CIO 3x3", "NBA Pro Skills"
    val timeAgo: String,
    val content: String,
    val category: PostCategory,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val commentsCount: Int = 0,
    val sharesCount: Int = 0,
    val highlightTag: String? = null,
    val bpmStat: String? = null,
    val comments: List<Comment> = emptyList()
)

data class Comment(
    val id: Long,
    val postId: Long,
    val authorName: String,
    val authorRole: String,
    val text: String,
    val timeAgo: String
)

data class Coach(
    val id: Long,
    val name: String,
    val title: String,
    val certification: String, // "FIBA Level 3 Coach", "CIO Performance", "NBA Development"
    val experienceYears: Int,
    val specialty: String,
    val rating: Float,
    val reviewsCount: Int,
    val hourlyRate: String,
    val bio: String,
    val availableSlots: List<String>
)

data class CoachSession(
    val id: Long,
    val coachName: String,
    val athleteName: String,
    val sessionDate: String,
    val sessionTime: String,
    val objective: String,
    val status: String, // "Confirmé", "En attente", "Terminé"
    val notes: String
)
