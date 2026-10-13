package com.example.data.model

data class FibaRule(
    val articleNumber: String,
    val title: String,
    val summary: String,
    val officialDetails: String,
    val cioNbaNote: String,
    val keyPoints: List<String>
)

data class TacticalPlay(
    val title: String,
    val type: String, // "Attaque 12s", "Défense Switch", "Sortie de balle"
    val description: String,
    val keyTiming: String,
    val diagramSteps: List<String>
)

object Fiba3x3Repository {
    val officialRules = listOf(
        FibaRule(
            articleNumber = "Art. 1 & 2",
            title = "Terrain & Ballon Officiel",
            summary = "Un seul panier sur un demi-terrain de 15m de large sur 11m de long. Ballon officiel taille 6 avec le poids de la taille 7.",
            officialDetails = "Le terrain possède une zone de restriction, un arc à 6m75 et une zone sans faute de charge sous le panier. Le ballon spécial 3x3 FIBA offre une adhérence maximale adaptée aux changements rapides de direction en extérieur et intérieur.",
            cioNbaNote = "Adopté officiellement par le CIO aux Jeux Olympiques de Tokyo 2020 et Paris 2024. Le format 3x3 NBA / BIG3 s'appuie également sur ces dimensions explosives.",
            keyPoints = listOf("Dimensions : 15m x 11m", "Ballon 3x3 spécifique (poids T7, taille T6)", "1 arceau à 3m05")
        ),
        FibaRule(
            articleNumber = "Art. 3",
            title = "Composition des Équipes & Pas de Coach sur le banc",
            summary = "4 joueurs par équipe (3 sur le terrain + 1 remplaçant). Aucun entraîneur n'est autorisé sur le terrain ni sur le banc pendant le jeu.",
            officialDetails = "Les remplacements s'effectuent à la volée pendant les ballons morts derrière la ligne opposée au panier. La règle d'interdiction de coaching direct renforce la responsabilité tactique des athlètes, rendant la préparation en amont sur Acash Connect primordiale.",
            cioNbaNote = "Règle stricte FIBA / CIO : les joueurs doivent s'auto-gérer tactiquement pendant les 10 minutes de jeu.",
            keyPoints = listOf("3 joueurs sur le terrain + 1 remplaçant", "Remplacements illimités sur ballon mort", "Aucun coach sur le banc pendant le match")
        ),
        FibaRule(
            articleNumber = "Art. 5 & 8",
            title = "Horloge de Tir de 12 Secondes",
            summary = "Chaque équipe dispose de seulement 12 secondes pour tenter un tir au panier dès qu'elle a le contrôle de la balle.",
            officialDetails = "Le shot clock de 12 secondes impose un rythme cardiaque extrême (BPM souvent supérieur à 180). Si le ballon touche l'anneau et que l'équipe attaquante prend le rebond offensif, l'horloge repart à 12 secondes.",
            cioNbaNote = "C'est la moitié des 24 secondes de la NBA / FIBA 5x5, créant le sport collectif le plus dense au monde.",
            keyPoints = listOf("12 secondes de possession", "Buzzer sonore immédiat", "Rebond offensif = nouveau cycle de 12s")
        ),
        FibaRule(
            articleNumber = "Art. 6 & 7",
            title = "Attribution des Points & Victoire Immédiate",
            summary = "1 point pour un panier à l'intérieur de l'arc, 2 points derrière l'arc de 6m75. 1 point par lancer franc. Victoire à 21 points !",
            officialDetails = "Le match se termine automatiquement dès qu'une équipe atteint 21 points (Sudden Death) ou au bout de 10 minutes de temps de jeu effectif. En cas d'égalité à la fin du temps réglementaire, une prolongation a lieu : la première équipe marquant 2 points gagne !",
            cioNbaNote = "Règle CIO/FIBA standard : 10 minutes ou premier à 21 pts.",
            keyPoints = listOf("1 pt à l'intérieur / lancer franc", "2 pts derrière la ligne des 6,75m", "Fin du match immédiate à 21 points", "Prolongation : 2 points marqués = victoire")
        ),
        FibaRule(
            articleNumber = "Art. 9",
            title = "Check-Ball & Sortie Derrière l'Arc",
            summary = "Après un panier marqué ou un ballon mort, reprise au sommet de l'arc avec check-ball. Après rebond défensif, sortir derrière l'arc.",
            officialDetails = "Si l'équipe en défense récupère le ballon par rebond ou interception, le porteur de balle doit impérativement dribbler ou faire une passe à un coéquipier situé derrière l'arc des 6,75m avant de pouvoir attaquer.",
            cioNbaNote = "Les deux pieds doivent être nettement dégagés derrière l'arc.",
            keyPoints = listOf("Check-ball au sommet de la raquette", "Clearance obligatoire derrière l'arc", "Pas d'arrêt de jeu après panier marqué : l'attaque démarre sous l'arceau")
        ),
        FibaRule(
            articleNumber = "Art. 13",
            title = "Fautes d'Équipe & Sanctions Sévères",
            summary = "Fautes 7, 8 et 9 = 2 lancers francs. À partir de la 10e faute = 2 lancers francs PLUS possession du ballon.",
            officialDetails = "Les fautes individuelles ne mènent pas à l'exclusion (sauf faute disqualifiante), mais les fautes d'équipe s'accumulent très vite. La 10e faute est quasiment rédhibitoire dans les matchs à haut enjeu.",
            cioNbaNote = "Système de pénalité intensif FIBA / CIO pour privilégier la fluidité et punir le jeu dur.",
            keyPoints = listOf("Fautes 1 à 6 : balle remise en jeu", "Fautes 7 à 9 : 2 lancers francs systématiques", "10e faute et + : 2 lancers francs + possession de balle")
        )
    )

    val tacticalDrills = listOf(
        TacticalPlay(
            title = "Short Roll & Kick-out 2-Pts",
            type = "Attaque 12s",
            description = "Écran ultra rapide à 8 secondes du shot clock. Le porteur pénètre, l'intérieur roll vers l'arceau pour fixer l'aide, puis passe instantanée dans le corner pour un tir à 2 points.",
            keyTiming = "Exécution en 4 secondes chrono",
            diagramSteps = listOf(
                "Porteur au sommet de l'arc avec check-ball",
                "Pose d'écran du pivot sur le défenseur extérieur",
                "Le défenseur switch : roll agressif du pivot",
                "Aide défensive aspirée sous l'arceau",
                "Kick-out passe vers l'ailier ouvert à 6m75 pour 2 pts"
            )
        ),
        TacticalPlay(
            title = "Défense Switch Agressif 'No-3' (No 2-pts)",
            type = "Défense FIBA",
            description = "En 3x3, un tir à 2 points rapporte le double d'un tir à 1 point (ratio 100% supérieur contre 50% en 5x5). La consigne absolue est le contest serré de tous les tirs derrière l'arc.",
            keyTiming = "Contestation continue sur 12 secondes",
            diagramSteps = listOf(
                "Alignement serré sur la ligne des 6m75",
                "Switch immédiat sur tout écran sans décrochage",
                "Interdiction d'aide depuis le tireur adverse",
                "Box-out physique et premier contrôle du ballon",
                "Dégagement rapide derrière l'arc"
            )
        ),
        TacticalPlay(
            title = "Transition Éclair Post-Panier",
            type = "Sortie de balle",
            description = "Selon les règles FIBA 3x3, après un panier adverse, pas de check-ball : le ballon est sorti immédiatement du filet et doit sortir de l'arc.",
            keyTiming = "Dégagement en moins de 2,5 secondes",
            diagramSteps = listOf(
                "Panier adverse marqué",
                "Récupération immédiate sous le filet sans sortir du terrain",
                "Passe directe au tireur démarqué derrière l'arc",
                "Tir immédiat ou relance d'attaque"
            )
        )
    )
}
