package com.example.data.model

data class AffiliatedSchool(
    val id: String,
    val name: String,
    val acronym: String,
    val category: String = "École Fondamental Affilée",
    val commune: String,
    val department: String = "Lwès (Ouest)",
    val addressOrZone: String,
    val totalStudents: Int,
    val activeAthletes: Int,
    val directorOrCoordinator: String,
    val contactPhone: String,
    val sportsDisciplines: List<String>,
    val homologation: String = "Homologué Comité Olympique International CIO",
    val description: String
)

object AffiliatedSchoolsData {
    val schoolsList: List<AffiliatedSchool> = listOf(
        AffiliatedSchool(
            id = "acash_fondamental_mixte",
            name = "ACASH École Fondamental Mixte",
            acronym = "ACASH EFM",
            commune = "Delmas",
            addressOrZone = "Dèlma 33 / Complexe Sportif ACASH, Port-au-Prince",
            totalStudents = 420,
            activeAthletes = 185,
            directorOrCoordinator = "Dir. Jean-Baptiste Dorson & Coach Ronald Pierre",
            contactPhone = "+509 37 12 4455",
            sportsDisciplines = listOf("Basketball 3X3", "Football", "Athlétisme", "Danse"),
            description = "Établissement fondamental pilote de l'Académie Sportive d'Haïti ACASH. Centre d'apprentissage académique et d'initiation aux normes olympiques FIBA 3X3 et CIO."
        ),
        AffiliatedSchool(
            id = "isd_delmas",
            name = "Institution Scolaire de Delmas ISD",
            acronym = "ISD",
            commune = "Delmas",
            addressOrZone = "Delmas 40B / Pétion-Ville Corridor",
            totalStudents = 580,
            activeAthletes = 210,
            directorOrCoordinator = "Dir. Nathalie Célestin & Coach Stanley Marc",
            contactPhone = "+509 38 22 3344",
            sportsDisciplines = listOf("Basketball 3X3", "Volleyball", "Athlétisme", "Tennis"),
            description = "Institution scolaire de référence à Delmas, réputée pour son encadrement fondamental rigoureux et ses équipes de basket 3x3 médaillées scolaires."
        ),
        AffiliatedSchool(
            id = "college_lavallon",
            name = "Collège Lavallon",
            acronym = "Lavallon",
            commune = "Port-au-Prince",
            addressOrZone = "Zone Lalue / Bois Verna, Port-au-Prince",
            totalStudents = 490,
            activeAthletes = 160,
            directorOrCoordinator = "Dir. Marc-Antoine Lavallon & Coach Patrick Similien",
            contactPhone = "+509 34 55 6677",
            sportsDisciplines = listOf("Basketball 3X3", "Football", "Danse & Gymnastique"),
            description = "Collège historique affilié ACASH intégrant le sport-études, le développement de la motricité fondamentale et la formation civique aux valeurs du CIO."
        ),
        AffiliatedSchool(
            id = "presbyterale_saint_pierre_sma",
            name = "École Presbytérale Saint Pierre SMA",
            acronym = "Saint Pierre SMA",
            commune = "Saint-Marc / SMA Area",
            addressOrZone = "Paroisse Saint Pierre SMA, Saint-Marc / Artibonite - Ouest Corridor",
            totalStudents = 370,
            activeAthletes = 145,
            directorOrCoordinator = "Père Responsable SMA & Coach Alix Gabriel",
            contactPhone = "+509 36 11 2233",
            sportsDisciplines = listOf("Basketball 3X3", "Athlétisme", "Football"),
            description = "École presbytérale engagée dans l'éducation des jeunes et l'inclusion par le sport, pionnière des tournois scolaires 3x3 inter-paroissiaux homologués."
        ),
        AffiliatedSchool(
            id = "nationale_congreganiste_sma",
            name = "École Nationale Congréganiste SMA",
            acronym = "Congréganiste SMA",
            commune = "Saint-Marc / SMA Area",
            addressOrZone = "Congrégation SMA, Secteur Éducatif Fondamental",
            totalStudents = 510,
            activeAthletes = 190,
            directorOrCoordinator = "Sœur Directrice & Coach Judeline Joseph",
            contactPhone = "+509 31 44 8899",
            sportsDisciplines = listOf("Basketball 3X3", "Volleyball", "Athlétisme", "Fair-Play"),
            description = "Établissement congréganiste axé sur la discipline, l'excellence académique et la préparation athlétique avec passeport médical certifié."
        ),
        AffiliatedSchool(
            id = "lycee_charlemagne_peralte_sma",
            name = "Lycée Charlemagne Peralte SMA",
            acronym = "LCP SMA",
            commune = "Saint-Marc / SMA Area",
            addressOrZone = "Boulevard Charlemagne Péralte, Section SMA",
            totalStudents = 760,
            activeAthletes = 275,
            directorOrCoordinator = "Censeur des Études & Coach Wilfrid Charles",
            contactPhone = "+509 35 66 7788",
            sportsDisciplines = listOf("Basketball 3X3", "Football", "Athlétisme Fondamental", "Volleyball"),
            description = "Lycée public d'élite de la communauté SMA formant la relève athlétique ACASH, avec un vivier exceptionnel pour les sélections régionales et nationales."
        )
    )
}
