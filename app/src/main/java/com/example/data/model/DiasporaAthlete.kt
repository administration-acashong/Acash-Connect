package com.example.data.model

data class DiasporaAthlete(
    val id: String,
    val fullName: String,
    val country: String,
    val city: String,
    val flagEmoji: String,
    val sport: String,
    val roleOrTitle: String,
    val clubOrAcademy: String,
    val bio: String,
    val keyAchievements: List<String>,
    val contributionToHaiti: String,
    val contactOrSocial: String,
    val isAmbassador: Boolean = false
)

object DiasporaRepository {
    val josueBienAime = DiasporaAthlete(
        id = "josue_bien_aime_canada",
        fullName = "Josué Bien Aimé",
        country = "Kanada (Canada)",
        city = "Montréal (Québec) & Toronto (Ontario)",
        flagEmoji = "🇨🇦",
        sport = "Basket 3X3 • Foutbòl • Preparasyon Fizik",
        roleOrTitle = "Lidè Kowòdinasyon Dyaspora ACASH & Anbasadè Entènasyonal",
        clubOrAcademy = "Montreal Hoops Academy / ACASH Diaspora Canada",
        bio = "Josué Bien Aimé se yon figi anblèm nan kominote ayisyen an nan Kanada. Kòm atlèt elit ak lidè kominotè, li konsakre enèji li pou bati pon ant dyaspora a ak jèn espòtif k ap grandi nan 10 depatman Ayiti yo. Li dirije pwogram kowòdinasyon pou distribye ekipman espòtif, fasilite bousdetid espòtif nan kolèj ak inivèsite kanadyen, epi òganize kan antrènman entènasyonal.",
        keyAchievements = listOf(
            "🏆 Chanpyon & MVP Tounwa 3X3 Kominotè Monreyal (2023, 2024)",
            "🍁 Fondatè Inisyativ 'Pon Espò Ayiti-Kanada'",
            "📦 Kòdonasyon voye plis pase 350 balon ak tenis espòtif bay klèb nan Sid, Lwès ak Nò Ayiti",
            "🎓 Mentò pou plis pase 40 jèn ayisyen ki rive nan pwogram etid-espò nan Kebèk ak Ontario",
            "🤝 Patnè estratejik ak federasyon ak lig espòtif kanadyèn pou deteksyon talan"
        ),
        contributionToHaiti = "Dirije kowòdinasyon jeneral dyaspora kanadyèn pou sipòte akademi ACASH sou teren an ann Ayiti. Li òganize sesyon coaching vityèl ak sipò medikal/lojistik pou tounwa nasyonal yo.",
        contactOrSocial = "josue.bienaime@acashconnect.org • Montreal, QC, Canada",
        isAmbassador = true
    )

    val otherDiasporaAthletes = listOf(
        DiasporaAthlete(
            id = "gaelle_pierre_usa",
            fullName = "Gaëlle Pierre",
            country = "Etazini (USA)",
            city = "Miami (Florida)",
            flagEmoji = "🇺🇸",
            sport = "Basket 3X3 & Atlètis Sprint 200m",
            roleOrTitle = "Atlèt NCAA & Kowòdonatris Jenès Sid Florid",
            clubOrAcademy = "Florida Storm Elite / Miami Hurricanes Club",
            bio = "Esprentè ak jwèz basket 3X3 ki patisipe nan kous entè-inivèsitè. Li reprezante fyète ayisyen an nan tounwa nasyonal ameriken yo.",
            keyAchievements = listOf(
                "Finalis 200m Florida State Championships",
                "Meneuse titilè nan seleksyon 3x3 U21 Miami"
            ),
            contributionToHaiti = "Finanse kan vakans espòtif pou ti medam nan komin Jakmèl ak Leyogàn.",
            contactOrSocial = "gaelle.p@acashconnect.org • Miami, FL",
            isAmbassador = false
        ),
        DiasporaAthlete(
            id = "jean_luc_baptiste_france",
            fullName = "Jean-Luc Baptiste",
            country = "Frans (France)",
            city = "Paris & Cergy-Pontoise",
            flagEmoji = "🇫🇷",
            sport = "Football & Basket 3X3",
            roleOrTitle = "Antrenè Fòmasyon & Egzaminatè FIBA",
            clubOrAcademy = "Paris Basket & Entente Cergy Football",
            bio = "Spesyalis nan metodoloji antrènman ewopeyen, li travay pou modènize pedagoji espòtif ak prepare jèn pou tès pwofesyonèl an Ewòp.",
            keyAchievements = listOf(
                "Diplome Brevet d'État d'Éducateur Sportif (BEES)",
                "Ko-òganizatè Open Plus 3X3 Paris-Île-de-France"
            ),
            contributionToHaiti = "Fòmasyon gratis a distans pou 50 antrenè nan depatman Nò ak Latibonit.",
            contactOrSocial = "jeanluc.b@acashconnect.org • Paris, France",
            isAmbassador = false
        ),
        DiasporaAthlete(
            id = "vanessa_celestin_rd",
            fullName = "Vanessa Célestin",
            country = "Repiblik Dominikèn (RD)",
            city = "Santo Domingo & Santiago",
            flagEmoji = "🇩🇴",
            sport = "Volley-ball & Beach Volley",
            roleOrTitle = "Kapitèn Ekip & Kowòdonatris Transfrontalyè",
            clubOrAcademy = "Club Mirador Voleibol Santo Domingo",
            bio = "Kowòdone tounwa volebòl amikal ant jèn dominiken ak ayisyen nan zòn fwontyè Wanament ak Dajabón pou ranfòse lapè ak fratènite.",
            keyAchievements = listOf(
                "Meyè Libero nan Lig Distri Nasyonal Santo Domingo 2023",
                "Inisyatris tounwa 'Volebòl san Fwontyè'"
            ),
            contributionToHaiti = "Lojistik ak akèy pou delegasyon espòtif ayisyen k ap pase Repiblik Dominikèn.",
            contactOrSocial = "vanessa.c@acashconnect.org • Santo Domingo, RD",
            isAmbassador = false
        ),
        DiasporaAthlete(
            id = "ricardo_sanon_canada",
            fullName = "Ricardo Sanon",
            country = "Kanada (Canada)",
            city = "Laval & Ottawa",
            flagEmoji = "🇨🇦",
            sport = "Tennis & Preparasyon Mantal",
            roleOrTitle = "Coach Tennis & Preparatè Mantal",
            clubOrAcademy = "Club de Tennis Laval-sur-le-Lac",
            bio = "Travay ak jenn jwè tenis sou jesyon estrès, kouray mantal nan tie-break ak estrateji jwèt sou tè batit ak siman.",
            keyAchievements = listOf(
                "Sètifye Tennis Canada Club Professional 2",
                "Antrenè de jèn finalis nan tounwa provinsyal Kebèk"
            ),
            contributionToHaiti = "Voye plis pase 50 rakèt ak bwat boul tenis nan klèb Petyonvil ak Okay.",
            contactOrSocial = "ricardo.s@acashconnect.org • Laval, QC, Canada",
            isAmbassador = false
        ),
        DiasporaAthlete(
            id = "sarah_desrosiers_usa",
            fullName = "Sarah Desrosiers",
            country = "Etazini (USA)",
            city = "Boston & New York",
            flagEmoji = "🇺🇸",
            sport = "Danse Sportive & Breaking",
            roleOrTitle = "B-Girl & Choregraf Entènasyonal",
            clubOrAcademy = "East Coast B-Boy & B-Girl Collective",
            bio = "Pasyonab pou Breaking ak dans rasin ayisyen, li montre kouman mouvman zansèt yo konekte ak kilti hip-hop mondyal la.",
            keyAchievements = listOf(
                "Gayan Red Bull BC One Cypher Boston 2023",
                "Choregraf pou plizyè videyo mizik ak spektak Broadway"
            ),
            contributionToHaiti = "Atelye Dans ak Breaking pou jèn nan Jakmèl ak Pòtoprens.",
            contactOrSocial = "sarah.d@acashconnect.org • Boston, MA",
            isAmbassador = false
        )
    )

    val allDiasporaAthletes: List<DiasporaAthlete>
        get() = listOf(josueBienAime) + otherDiasporaAthletes
}
