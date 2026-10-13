package com.example.data.model

data class SportPosition(
    val title: String,
    val description: String,
    val keySkills: String
)

data class SportDrill(
    val name: String,
    val durationMinutes: Int,
    val intensityZone: String, // ex: "Zone 170-190 BPM", "Zone VMA"
    val description: String,
    val steps: List<String>
)

data class SportDiscipline(
    val id: String,
    val name: String,
    val kreylName: String,
    val iconEmoji: String,
    val tagline: String,
    val federation: String,
    val overview: String,
    val olympicStatus: String,
    val positions: List<SportPosition>,
    val drills: List<SportDrill>,
    val keyRules: List<String>,
    val targetBpmRange: String
)

object MultiSportData {
    val sportsList = listOf(
        SportDiscipline(
            id = "football",
            name = "Football",
            kreylName = "Foutbòl",
            iconEmoji = "⚽",
            tagline = "Espò wa a • Teknik, vitès, ak vizyon jwèt sou gwo tèren",
            federation = "FIFA • CONCACAF • FHF (Fédération Haïtienne de Football)",
            overview = "Foutbòl se espò ki pi popilè ann Ayiti. Li mande gwo andirans kadyovaskilè, ekselan manyen balon, vitès fowle, ak entèlijans taktik pou kreye oswa bloke okazyon gòl.",
            olympicStatus = "Espò Olenpik Ofisyèl",
            targetBpmRange = "165 - 190 BPM",
            positions = listOf(
                SportPosition("Atakè Pwent / Ailier", "Fini okazyon, fè apèl nan do defans, eksplozivite sou kote", "Finition, Vitesse, Dribble"),
                SportPosition("Mitan Relayeur / 10", "Distribye balon, kontwole ritm match la, konekte defans ak atak", "Vision, Passes courtes & longues, VMA"),
                SportPosition("Defansè Santral / Latéral", "Entèsepsyon, tèt defansif, kontwòl zòn, relans pwòp", "Tacles, Duel aérien, Puissance"),
                SportPosition("Gadyen But", "Reflèks rapid, kominikasyon, relans nan men ak pye", "Plongeons, Relance, Leadership")
            ),
            drills = listOf(
                SportDrill(
                    name = "Tir an kous apre kontwòl oryante",
                    durationMinutes = 20,
                    intensityZone = "Zone Anaérobie (180 BPM)",
                    description = "Travay sou rapidite ekzekisyon devan kaj la.",
                    steps = listOf("Resevwa pas fò sou kote", "Fè yon premye kontwòl oryante nan espas la", "Frape nan kourwa anba 2 segonn")
                ),
                SportDrill(
                    name = "Sikwi Pase Kout & Presing 4v2 (Rondo)",
                    durationMinutes = 15,
                    intensityZone = "Zone Aérobie & Réactivité",
                    description = "Devlope reyaksyon rapid nan ti espas ak presizyon pas.",
                    steps = listOf("4 jwè sou bò, 2 nan mitan ap chèche vòlè balon", "Maksimòm 2 touche balon", "Ogmante ritm chak 2 minit")
                )
            ),
            keyRules = listOf(
                "Match 90 minit (2 fwa 45 min)",
                "11 jwè sou teren an pou chak ekip",
                "Règ Hors-jeu (òsayd) aktif",
                "Katon jòn ak katon wouj pou disiplin"
            )
        ),
        SportDiscipline(
            id = "volleyball",
            name = "Volley-ball",
            kreylName = "Volebòl",
            iconEmoji = "🏐",
            tagline = "Detant vètikal, kowòdinasyon kolektif ak smash zèklè",
            federation = "FIVB • NORCECA • FHVB",
            overview = "Volebòl se yon espò san kontak dirèk kote chak pwen depann sou kowòdinasyon egzak ant 6 jwè. Li devlope gwo detant vètikal, reflèks manchette, ak pouvwa frape nan filè a.",
            olympicStatus = "Espò Olenpik Ofisyèl (Sal & Beach Volley)",
            targetBpmRange = "160 - 185 BPM",
            positions = listOf(
                SportPosition("Atakè Pwent / Pointu", "Prensipal zam ofansif ekip la pou fini boul difisil", "Détente verticale, Puissance smash"),
                SportPosition("Pasè (Passeur)", "Sèvo ekip la ki deside distribisyon chak balon", "Toucher de balle précis, Tactique"),
                SportPosition("Mitan Santral (Central)", "Bloke atak advèsè ak fè atak rapid nan mitan", "Blocage réflexe, Vitesse latérale"),
                SportPosition("Libero", "Espesyalis defans ak resèpsyon, pa gen dwa atake", "Manchette réflexe, Plongeon spectaculaire")
            ),
            drills = listOf(
                SportDrill(
                    name = "Plyometri & Detant Smash sou Filè",
                    durationMinutes = 25,
                    intensityZone = "Zone Puissance Explosive",
                    description = "Amelyore wotè sote pou smash ak blok.",
                    steps = listOf("Sote sou bwat plyometrik 60cm", "Enchainman 3 pas kous d'elan", "Smash ak kase ponyèt anba filè a")
                ),
                SportDrill(
                    name = "Sikwi Sèvis Flotan & Resèpsyon Manchette",
                    durationMinutes = 20,
                    intensityZone = "Zone Précision & Gainage",
                    description = "Mete presyon sou advèsè depi nan liy fon an.",
                    steps = listOf("Sèvis flotan sou zòn sib espesifik", "Deplasman rapid pou resevwa nan manchette", "Voye boul la nan zòn pasè a")
                )
            ),
            keyRules = listOf(
                "Match jwe an 3 set gagnan (25 pwen pa set, tie-break 15 pts)",
                "Maksimòm 3 touche balon anvan voye l lòt bò",
                "Entèdi pou kenbe oswa pote balon an",
                "Wotasyon obligatwa nan sans zegwi mont lè w pran sèvis la"
            )
        ),
        SportDiscipline(
            id = "tennis",
            name = "Tennis",
            kreylName = "Tenis",
            iconEmoji = "🎾",
            tagline = "Fòs mantal, deplasman milimetrik ak presizyon rakèt",
            federation = "ITF • ATP / WTA • FHT",
            overview = "Tenis se yon espò endividyèl oswa an doub ki mande yon disiplin fizik ak mantal ekstraòdinè. Li konbine deplasman lateral rapid, puisans sèvis, ak kout dwat lifte pou domine advèsè a.",
            olympicStatus = "Espò Olenpik Ofisyèl",
            targetBpmRange = "155 - 188 BPM",
            positions = listOf(
                SportPosition("Jwè Fon de Kour Agresif", "Enpoze ritm ak kout dwat lou depi liy fon an", "Coup droit lifté, Endurance fond de court"),
                SportPosition("Atakè Voleyè / Sèvè-Voleyè", "Moute rapidman nan filè apre sèvis pou fini pwen an", "Volée réflexe, Service puissant"),
                SportPosition("Kontratakè Polyvalan", "Defann tout boul, li jwèt advèsè a epi itilize kout kontre", "Jeu de jambes, Constance mentale")
            ),
            drills = listOf(
                SportDrill(
                    name = "Sikwi Split-Step & Kout Dwat Lifte",
                    durationMinutes = 25,
                    intensityZone = "Zone Vitesse & Anaérobie",
                    description = "Travay sou premye pa eksplozif pou frape boul an tèt rebò.",
                    steps = listOf("Split-step lè advèsè a frape", "Plasman pye nan 'open stance'", "Travese kò a ak rakèt la pou mete lift")
                ),
                SportDrill(
                    name = "Sèvis Premye Boul & Vole Amòti",
                    durationMinutes = 20,
                    intensityZone = "Zone Précision & Coordination",
                    description = "Chache pwen dirèk ak varyasyon efè.",
                    steps = listOf("Lanse boul nan pwen pi wo", "Pran ang kwaze oswa sou kò jwè a", "Avansman rapid pou depoze yon amòti")
                )
            ),
            keyRules = listOf(
                "Sistèm pwen : 15, 30, 40, Jwèt, Set (6 jwèt)",
                "Boul la dwe rebondi yon sèl fwa anndan liy yo",
                "2 tantativ sèvis pou chak pwen",
                "Tie-break a 6-6 (premye a 7 pwen ak 2 pwen diferans)"
            )
        ),
        SportDiscipline(
            id = "danse",
            name = "Danse Sportive & Breaking",
            kreylName = "Dans Espòtif & Breaking",
            iconEmoji = "💃🕺",
            tagline = "Atizay, ritm kreyòl, akwobasi ak ekspresyon kòporèl olenpik",
            federation = "WDSF (World DanceSport) • CIO (Paris 2024 Olympic Breaking)",
            overview = "Dans espòtif ak Breaking konbine atletis ekstrèm ak mizikalite. Ant Toprock, Footwork, Power moves ak Freezes, atlèt la eksprime kilti li pandan l ap boule plis pase 600 kalori pou chak èdtan antrènman entansif.",
            olympicStatus = "Disiplin Olenpik Ofisyèl (Jeux Olympiques Paris 2024)",
            targetBpmRange = "170 - 195 BPM",
            positions = listOf(
                SportPosition("B-Boy / B-Girl (Breaking Olenpik)", "Dansè akwobatik ki metrize tè a, vire sou tèt ak freezes", "Power moves, Footwork, Freeze, Musicalité"),
                SportPosition("Dansè Afro-Kreyòl & Ritm Tradisyonèl", "Konekte rasin kiltirèl ayisyen ak mouvman modèn", "Rythme racine, Souplesse, Énergie scénique"),
                SportPosition("Freestyle & Hip-Hop Choreography", "Kreyasyon sekans mouvman rapid sou tout kalite tanpo", "Isolation corporelle, Flow, Synchronisation")
            ),
            drills = listOf(
                SportDrill(
                    name = "Enchainman 6-Step Footwork & Baby Freeze",
                    durationMinutes = 20,
                    intensityZone = "Zone Cardio & Gainage Ekstrèm",
                    description = "Baz fondamantal Breaking sou planche.",
                    steps = listOf("Kòmanse nan pozisyon kwoupi", "Ekzekite 6-step sikilè san kanpe", "Bloke nan Baby Freeze pou 5 segonn")
                ),
                SportDrill(
                    name = "Toprock Eksplozif & Batay Ritmic",
                    durationMinutes = 15,
                    intensityZone = "Zone Fréquence Cardiaque Élevée",
                    description = "Mete aksan sou prezans sou sèn ak koneksyon ak mizik la.",
                    steps = listOf("Cross step & Indian step sou tanpo 120 BPM", "Chanjman nivo tè-wotè rapid", "Ekspresyon stil ak konfyans")
                )
            ),
            keyRules = listOf(
                "Batay 1v1 devan jiri jij kalifye",
                "Kritè nòt : Teknik, Mizikalite, Orijinalite, Ekzekisyon",
                "Entèdi pou manyen advèsè a pandan batay la",
                "Repons dirèk sou mizik DJ a chwazi san avètisman"
            )
        ),
        SportDiscipline(
            id = "athletisme",
            name = "Athlétisme",
            kreylName = "Atlètis",
            iconEmoji = "🏃💨",
            tagline = "Pi vit, pi wo, pi fò • Vitès pi bon kalite, andirans ak franchisman",
            federation = "World Athletics • NACAC • Fédération Haïtienne d'Athlétisme (FHA)",
            overview = "Atlètis se fondasyon tout espò sou tè a. Li rasanble kous vitès (100m, 200m), demi-fond (400m, 800m), ak konkou sote (longè, wotè). Li fòje yon kò solid ak yon volonte an fè.",
            olympicStatus = "Poto Mitan Tout Jeux Olympiques",
            targetBpmRange = "175 - 205 BPM",
            positions = listOf(
                SportPosition("Esprentè (100m / 200m)", "Puisans pi bon kalite, depa fulgiran nan starting-blocks", "Vitesse pure, Puissance anaérobie alactique"),
                SportPosition("Kourè Demi-Fond (400m / 800m / 1500m)", "Rezistans a asid laktik, jesyon efò sou plizyè tou", "Endurance lactique, VMA élevée, Stratégie"),
                SportPosition("Sotè Longè & Tripl-Sot", "Kous d'elan rapid ak apèl eksplozif sou planch la", "Coordination impulsion, Vitesse d'approche"),
                SportPosition("Sotè Wotè (Fosbury Flop)", "Vire do sou ba a ak franchisman san touche", "Souplesse dorsale, Timing d'impulsion")
            ),
            drills = listOf(
                SportDrill(
                    name = "Depa nan Starting-Blocks & Akselerasyon 30m",
                    durationMinutes = 25,
                    intensityZone = "Zone Puissance Maximale (100% Efò)",
                    description = "Optimize premye foul yo pou soti tankou yon flèch.",
                    steps = listOf("Pozisyon 'A vos marques' ak 'Prêts'", "Pouse eksplozif ak 2 pye yo", "Kenbe tèt ba sou premye 15 mèt yo")
                ),
                SportDrill(
                    name = "Fraktyone 6 x 150m Vitès & Rekiperasyon",
                    durationMinutes = 30,
                    intensityZone = "Zone Lactique Intégrale",
                    description = "Konstwi rezistans pou kenbe vitès maksimòm jiska liy fini an.",
                    steps = listOf("Kouri 150m a 90% vitès", "Rekiperasyon aktif 2 minit mach", "Repete 6 fwa avèk menm kwono a")
                )
            ),
            keyRules = listOf(
                "Faux départ = disqualification immédiate en sprint",
                "Chak kourè dwe rete nan koulwa li pou kous kout yo",
                "Mòde sou planch nan sote longè = esè nul",
                "Transmisyon temwen nan zòn 20m pou kous relais"
            )
        )
    )
}
