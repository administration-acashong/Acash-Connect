package com.example.data.model

data class CommuneAthleteData(
    val communeName: String,
    val kreylName: String,
    val totalAthletes: Int,
    val basketballCount: Int,
    val footballCount: Int,
    val volleyballCount: Int,
    val athleticsCount: Int,
    val danceCount: Int,
    val tennisCount: Int,
    val femaleAthletes: Int,
    val maleAthletes: Int,
    val activeClubs: Int,
    val coordinatorName: String,
    val coordinatorPhone: String
)

data class DepartmentNetwork(
    val departmentId: String,
    val departmentName: String,
    val kreylName: String,
    val capitalCity: String,
    val regionalCoordinator: String,
    val coordinatorPhone: String,
    val regionalOffice: String,
    val communes: List<CommuneAthleteData>,
    val highlights: String
) {
    val totalAthletes: Int
        get() = communes.sumOf { it.totalAthletes }

    val totalFemaleAthletes: Int
        get() = communes.sumOf { it.femaleAthletes }

    val totalMaleAthletes: Int
        get() = communes.sumOf { it.maleAthletes }

    val totalClubs: Int
        get() = communes.sumOf { it.activeClubs }
}

object DepartmentNetworkRepository {
    val departments = listOf(
        DepartmentNetwork(
            departmentId = "lwes",
            departmentName = "Ouest",
            kreylName = "Lwès",
            capitalCity = "Pòtoprens (Port-au-Prince)",
            regionalCoordinator = "Mèt Jean-Baptiste Dorson",
            coordinatorPhone = "+509 37 12 4455",
            regionalOffice = "Sant Espòtif ACASH, Dèlma 33 / Channmas",
            highlights = "Pi gwo konsantrasyon klèb 3X3 ak jèn talan, kote fòmasyon entansif FIBA ak tounwa entè-komin fèt chak wikenn.",
            communes = listOf(
                CommuneAthleteData("Port-au-Prince", "Pòtoprens", 145, 60, 40, 20, 15, 10, 0, 58, 87, 8, "Coach Ronald Pierre", "+509 36 11 2233"),
                CommuneAthleteData("Delmas", "Dèlma", 112, 50, 30, 15, 10, 7, 0, 45, 67, 6, "Coach Nathalie Célestin", "+509 38 22 3344"),
                CommuneAthleteData("Pétion-Ville", "Petyonvil", 98, 42, 26, 12, 10, 8, 0, 40, 58, 5, "Coach Stanley Marc", "+509 34 55 6677"),
                CommuneAthleteData("Carrefour", "Kafou", 87, 36, 25, 14, 8, 4, 0, 32, 55, 4, "Coach Judeline Joseph", "+509 31 44 8899"),
                CommuneAthleteData("Tabarre", "Taba", 54, 24, 16, 8, 4, 2, 0, 20, 34, 3, "Coach Jean Rémy", "+509 39 00 1122"),
                CommuneAthleteData("Cité Soleil", "Site Solèy", 65, 30, 20, 8, 5, 2, 0, 22, 43, 3, "Coach Patrick Similien", "+509 37 88 9900"),
                CommuneAthleteData("Léogâne", "Leyogàn", 42, 18, 14, 6, 4, 0, 0, 16, 26, 3, "Coach Manuela Brun", "+509 36 55 4433"),
                CommuneAthleteData("Gressier", "Gresye", 28, 12, 10, 4, 2, 0, 0, 10, 18, 2, "Coach David Louis", "+509 32 11 9988"),
                CommuneAthleteData("Kenscoff", "Kenskòf", 31, 14, 10, 4, 3, 0, 0, 12, 19, 2, "Coach Alix Gabriel", "+509 33 22 5544"),
                CommuneAthleteData("Croix-des-Bouquets", "Kwadèboukè", 76, 32, 24, 10, 6, 4, 0, 28, 48, 4, "Coach Frantz Desrosiers", "+509 35 66 7788"),
                CommuneAthleteData("Arcahaie", "Akayè", 34, 14, 12, 5, 3, 0, 0, 12, 22, 2, "Coach Guerline Toussaint", "+509 38 44 1122"),
                CommuneAthleteData("Cabaret", "Kabarè", 25, 10, 9, 4, 2, 0, 0, 9, 16, 2, "Coach Junior Paul", "+509 39 33 2211")
            )
        ),
        DepartmentNetwork(
            departmentId = "no",
            departmentName = "Nord",
            kreylName = "Nò",
            capitalCity = "Okap (Cap-Haïtien)",
            regionalCoordinator = "Ing. Patrick Sanon",
            coordinatorPhone = "+509 37 45 9900",
            regionalOffice = "Gymnase Champ-de-Mars Okap / Bwèbèk",
            highlights = "Kilti espòtif rich ak tounwa 3x3 istorik sou Bòdmè Okap la, akonpanye ak yon gwo akademi atletis.",
            communes = listOf(
                CommuneAthleteData("Cap-Haïtien", "Okap", 118, 52, 32, 16, 12, 6, 0, 46, 72, 6, "Coach Wilfrid Charles", "+509 36 22 7788"),
                CommuneAthleteData("Limbé", "Lenbe", 34, 14, 12, 5, 3, 0, 0, 12, 22, 2, "Coach Carole Étienne", "+509 38 11 3322"),
                CommuneAthleteData("Plaine-du-Nord", "Plèn dinò", 28, 12, 10, 4, 2, 0, 0, 10, 18, 2, "Coach Michel Saint-Fleur", "+509 34 99 8877"),
                CommuneAthleteData("Acul-du-Nord", "Akil dinò", 22, 9, 8, 3, 2, 0, 0, 8, 14, 1, "Coach Samuel Dorvil", "+509 31 88 5544"),
                CommuneAthleteData("Dondon", "Dondon", 19, 8, 7, 2, 2, 0, 0, 7, 12, 1, "Coach Josette Philogène", "+509 39 77 4433"),
                CommuneAthleteData("Bas-Limbé", "Ba Lenbe", 16, 7, 6, 2, 1, 0, 0, 6, 10, 1, "Coach Jean-Marie Petit", "+509 37 66 3322"),
                CommuneAthleteData("Pignon", "Piyon", 24, 10, 9, 3, 2, 0, 0, 9, 15, 2, "Coach Pierre-Richard Noël", "+509 35 55 2211")
            )
        ),
        DepartmentNetwork(
            departmentId = "nodes",
            departmentName = "Nord-Est",
            kreylName = "Nòdès",
            capitalCity = "Fòlibète (Fort-Liberté)",
            regionalCoordinator = "Madanm Yolande Célestin",
            coordinatorPhone = "+509 38 90 1234",
            regionalOffice = "Sant Kominotè Wanament / Fòlibète",
            highlights = "Kafou komèsyal ak transfrontalyè ak Repiblik Dominikèn, kote genyen gwo echanj espòtif ak tounwa entènasyonal.",
            communes = listOf(
                CommuneAthleteData("Ouanaminthe", "Wanament", 64, 28, 18, 10, 5, 3, 0, 24, 40, 4, "Coach Édouard Blaise", "+509 36 99 0011"),
                CommuneAthleteData("Fort-Liberté", "Fòlibète", 38, 16, 12, 6, 4, 0, 0, 14, 24, 2, "Coach Marie-Ange Vixamar", "+509 34 88 7766"),
                CommuneAthleteData("Trou-du-Nord", "Twou dinò", 29, 12, 10, 4, 3, 0, 0, 11, 18, 2, "Coach Daniel Lubin", "+509 31 77 6655"),
                CommuneAthleteData("Terrier-Rouge", "Tèrye Wouj", 21, 9, 7, 3, 2, 0, 0, 8, 13, 1, "Coach Kettly Faustin", "+509 39 66 5544"),
                CommuneAthleteData("Mont-Organisé", "Montòganize", 17, 7, 6, 2, 2, 0, 0, 6, 11, 1, "Coach André Mondésir", "+509 37 55 4433")
            )
        ),
        DepartmentNetwork(
            departmentId = "nodwes",
            departmentName = "Nord-Ouest",
            kreylName = "Nòdwès",
            capitalCity = "Pòdpè (Port-de-Paix)",
            regionalCoordinator = "Pastè Gérard Moïse",
            coordinatorPhone = "+509 36 78 9012",
            regionalOffice = "Paren Espòtif Pòdpè / Lari Stenyo Vensan",
            highlights = "Zòn kotyè ki gen gwo talan nan vitès, rezistans ak espò sou sab (Beach Volley & Basket 3X3).",
            communes = listOf(
                CommuneAthleteData("Port-de-Paix", "Pòdpè", 72, 32, 20, 11, 6, 3, 0, 28, 44, 4, "Coach Fabrice Dumont", "+509 38 77 6655"),
                CommuneAthleteData("Saint-Louis-du-Nord", "Sen Lwi dinò", 31, 13, 10, 5, 3, 0, 0, 12, 19, 2, "Coach Nadia Chery", "+509 34 66 5544"),
                CommuneAthleteData("Jean-Rabel", "Jan Rabèl", 26, 11, 9, 4, 2, 0, 0, 10, 16, 2, "Coach Sony Metellus", "+509 31 55 4433"),
                CommuneAthleteData("Môle-Saint-Nicolas", "Mòl Sen Nikola", 22, 9, 8, 3, 2, 0, 0, 8, 14, 1, "Coach Jude Beaubrun", "+509 39 44 3322"),
                CommuneAthleteData("Bombardopolis", "Bonbadopolis", 18, 7, 6, 3, 2, 0, 0, 7, 11, 1, "Coach Rose-Merline Pierre", "+509 37 33 2211"),
                CommuneAthleteData("Bassin-Bleu", "Basen Ble", 19, 8, 7, 2, 2, 0, 0, 7, 12, 1, "Coach Enock Cadet", "+509 35 22 1100")
            )
        ),
        DepartmentNetwork(
            departmentId = "latibonit",
            departmentName = "Artibonite",
            kreylName = "Latibonit",
            capitalCity = "Gonayiv (Gonaïves)",
            regionalCoordinator = "Mèt Dieudonné Lamy",
            coordinatorPhone = "+509 37 89 0123",
            regionalOffice = "Sant Devlopman Jenès Gonayiv / Sen Mak",
            highlights = "Kè agrikòl peyi a avèk jèn atlèt ki gen gwo fòs fizik natirèl ak pasyon san limit pou espò kolektif.",
            communes = listOf(
                CommuneAthleteData("Gonaïves", "Gonayiv", 89, 40, 26, 13, 7, 3, 0, 35, 54, 5, "Coach Jean-Claude Altidor", "+509 36 88 1122"),
                CommuneAthleteData("Saint-Marc", "Sen Mak", 78, 34, 24, 12, 6, 2, 0, 30, 48, 4, "Coach Marjorie Denis", "+509 38 66 2233"),
                CommuneAthleteData("Dessalines", "Desalin", 44, 18, 14, 7, 4, 1, 0, 16, 28, 2, "Coach Watson Sylvestre", "+509 34 44 3344"),
                CommuneAthleteData("Verrettes", "Vèrèt", 36, 15, 12, 5, 3, 1, 0, 14, 22, 2, "Coach Mirlande Occil", "+509 31 33 4455"),
                CommuneAthleteData("Gros-Morne", "Gwo Mòn", 31, 13, 10, 5, 3, 0, 0, 12, 19, 2, "Coach Frantzky Belval", "+509 39 22 5566"),
                CommuneAthleteData("Ennery", "Anchery", 23, 10, 8, 3, 2, 0, 0, 9, 14, 1, "Coach Chantal Balthazar", "+509 37 11 6677"),
                CommuneAthleteData("Saint-Michel-de-l'Attalaye", "Sen Michèl", 28, 12, 9, 4, 3, 0, 0, 10, 18, 2, "Coach Guy-Serge Jean", "+509 35 00 7788")
            )
        ),
        DepartmentNetwork(
            departmentId = "sant",
            departmentName = "Centre",
            kreylName = "Sant",
            capitalCity = "Ench (Hinche)",
            regionalCoordinator = "Agronòm Gary Paul",
            coordinatorPhone = "+509 36 67 8901",
            regionalOffice = "Plato Santral Sport Hub / Ench",
            highlights = "Plato santral la genyen kous andirans ekselan ak jèn ki gen gwo kapasite kadyorespiratwa grasa altitid la.",
            communes = listOf(
                CommuneAthleteData("Hinche", "Ench", 58, 26, 18, 8, 4, 2, 0, 22, 36, 3, "Coach Fedner Clervil", "+509 38 55 9900"),
                CommuneAthleteData("Mirebalais", "Mibalè", 49, 22, 15, 7, 4, 1, 0, 19, 30, 3, "Coach Roseline Hyppolite", "+509 34 33 8899"),
                CommuneAthleteData("Lascahobas", "Laskawobas", 33, 14, 11, 5, 3, 0, 0, 13, 20, 2, "Coach Jimmy François", "+509 31 22 7788"),
                CommuneAthleteData("Belladère", "Beladè", 27, 12, 9, 4, 2, 0, 0, 10, 17, 2, "Coach Yolène Métayer", "+509 39 11 6677"),
                CommuneAthleteData("Boucan-Carré", "Boukan Kare", 19, 8, 6, 3, 2, 0, 0, 7, 12, 1, "Coach Peterson Durosier", "+509 37 00 5566"),
                CommuneAthleteData("Cerca-la-Source", "Sèka Lasous", 16, 7, 5, 2, 2, 0, 0, 6, 10, 1, "Coach Marie-Lourdes Jean", "+509 35 99 4455")
            )
        ),
        DepartmentNetwork(
            departmentId = "sid",
            departmentName = "Sud",
            kreylName = "Sid",
            capitalCity = "Okay (Les Cayes)",
            regionalCoordinator = "Doktè Maxo Bernard",
            coordinatorPhone = "+509 38 34 5678",
            regionalOffice = "Sant Olenpik Bèlrivyè / Okay",
            highlights = "Yon sant espòtif istorik pou Sid la, ak gwo fòmasyon nan basket, volley-ball ak espò kotyè nan Pòsali.",
            communes = listOf(
                CommuneAthleteData("Les Cayes", "Okay", 82, 36, 24, 13, 6, 3, 0, 32, 50, 4, "Coach Jean-Gilles Voltaire", "+509 36 77 3344"),
                CommuneAthleteData("Aquin", "Aken", 36, 15, 12, 5, 3, 1, 0, 14, 22, 2, "Coach Bernadette Philistin", "+509 38 44 2211"),
                CommuneAthleteData("Port-Salut", "Pòsali", 29, 13, 9, 5, 2, 0, 0, 11, 18, 2, "Coach Wilner Labissière", "+509 34 22 1199"),
                CommuneAthleteData("Coteaux", "Koto", 21, 9, 7, 3, 2, 0, 0, 8, 13, 1, "Coach Gina Desamours", "+509 31 11 0088"),
                CommuneAthleteData("Chantal", "Chantal", 18, 8, 6, 2, 2, 0, 0, 7, 11, 1, "Coach Raymond Dérice", "+509 39 00 9977"),
                CommuneAthleteData("Torbeck", "Torbèk", 24, 10, 8, 4, 2, 0, 0, 9, 15, 2, "Coach Florence Samedi", "+509 37 99 8866"),
                CommuneAthleteData("Tiburon", "Tibiwon", 17, 7, 6, 2, 2, 0, 0, 6, 11, 1, "Coach Carlo Bien-Aimé", "+509 35 88 7755")
            )
        ),
        DepartmentNetwork(
            departmentId = "sides",
            departmentName = "Sud-Est",
            kreylName = "Sidès",
            capitalCity = "Jakmèl (Jacmel)",
            regionalCoordinator = "Madanm Chantal Khawly",
            coordinatorPhone = "+509 37 23 4567",
            regionalOffice = "Sant Kiltirèl ak Espòtif Jakmèl",
            highlights = "Jakmèl marye kilti, atizay, dans ak espò. Gwo pèfòmans nan Breaking ak tounwa 3x3 sou waf la.",
            communes = listOf(
                CommuneAthleteData("Jacmel", "Jakmèl", 75, 32, 22, 11, 6, 4, 0, 30, 45, 4, "Coach Michelet César", "+509 36 66 1122"),
                CommuneAthleteData("Marigot", "Marigo", 32, 14, 10, 5, 3, 0, 0, 12, 20, 2, "Coach Danielle Pierre", "+509 38 33 0011"),
                CommuneAthleteData("Belle-Anse", "Bèlans", 26, 11, 8, 4, 3, 0, 0, 10, 16, 2, "Coach Joseph Morisset", "+509 34 11 9900"),
                CommuneAthleteData("Côtes-de-Fer", "Kòt defè", 22, 9, 7, 3, 2, 1, 0, 8, 14, 1, "Coach Marie-Renée Cadet", "+509 31 00 8899"),
                CommuneAthleteData("Cayes-Jacmel", "Kay Jakmèl", 28, 12, 9, 4, 3, 0, 0, 11, 17, 2, "Coach Fresnel Lamothe", "+509 39 99 7788"),
                CommuneAthleteData("Bainet", "Benè", 19, 8, 6, 3, 2, 0, 0, 7, 12, 1, "Coach Sandra Mondésir", "+509 37 88 6677")
            )
        ),
        DepartmentNetwork(
            departmentId = "grandans",
            departmentName = "Grand'Anse",
            kreylName = "Grandans",
            capitalCity = "Jeremi (Jérémie)",
            regionalCoordinator = "Enjenyè Pierre-Louis Sanon",
            coordinatorPhone = "+509 36 12 3456",
            regionalOffice = "Plaza Espòtif Jeremi / Lari Pont",
            highlights = "Vil powèt yo ki fòme atlèt detèmine avèk gwo kouraj ak andirans nan kondisyon natirèl.",
            communes = listOf(
                CommuneAthleteData("Jérémie", "Jeremi", 68, 30, 20, 10, 5, 3, 0, 26, 42, 4, "Coach Lucner François", "+509 38 22 8899"),
                CommuneAthleteData("Moron", "Moron", 24, 10, 8, 3, 2, 1, 0, 9, 15, 2, "Coach Guerda Bazile", "+509 34 00 7788"),
                CommuneAthleteData("Anse-d'Hainault", "Ansdeno", 29, 12, 10, 4, 3, 0, 0, 11, 18, 2, "Coach Jean-Robert Zéphir", "+509 31 99 6677"),
                CommuneAthleteData("Dame-Marie", "Dam Mari", 26, 11, 9, 4, 2, 0, 0, 10, 16, 2, "Coach Judith Chéry", "+509 39 88 5566"),
                CommuneAthleteData("Pestel", "Pestèl", 18, 8, 6, 2, 2, 0, 0, 7, 11, 1, "Coach Samson Delva", "+509 37 77 4455"),
                CommuneAthleteData("Abricots", "Abriko", 15, 6, 5, 2, 2, 0, 0, 6, 9, 1, "Coach Micheline Guerrier", "+509 35 66 3344")
            )
        ),
        DepartmentNetwork(
            departmentId = "nip",
            departmentName = "Nippes",
            kreylName = "Nip",
            capitalCity = "Miragwàn (Miragoâne)",
            regionalCoordinator = "Avoka Jean-Claude Toussaint",
            coordinatorPhone = "+509 37 01 2345",
            regionalOffice = "Sant Devlopman Rejyonal Nip / Miragwàn",
            highlights = "Depatman ki pi jèn ann Ayiti kote jèn yo montre yon dinamism remakab nan basket ak foutbòl.",
            communes = listOf(
                CommuneAthleteData("Miragoâne", "Miragwàn", 48, 21, 15, 7, 3, 2, 0, 19, 29, 3, "Coach Serge Bélizaire", "+509 36 55 9900"),
                CommuneAthleteData("Anse-à-Veau", "Ansavo", 27, 12, 9, 4, 2, 0, 0, 10, 17, 2, "Coach Patricia Noël", "+509 38 11 8877"),
                CommuneAthleteData("Petit-Trou-de-Nippes", "Ti Twou de Nip", 21, 9, 7, 3, 2, 0, 0, 8, 13, 1, "Coach Wilfrid Exantus", "+509 34 99 7766"),
                CommuneAthleteData("Paillant", "Payan", 18, 8, 6, 2, 2, 0, 0, 7, 11, 1, "Coach Marie-Flore Denis", "+509 31 88 6655"),
                CommuneAthleteData("Fond-des-Nègres", "Fon dè Nèg", 24, 10, 8, 4, 2, 0, 0, 9, 15, 2, "Coach Alin Siméon", "+509 39 77 5544"),
                CommuneAthleteData("Baradères", "Baradè", 16, 7, 5, 2, 2, 0, 0, 6, 10, 1, "Coach Jacqueline Vaval", "+509 37 66 4433")
            )
        )
    )

    val grandTotalAthletes: Int = departments.sumOf { it.totalAthletes }
    val grandTotalCommunes: Int = departments.sumOf { it.communes.size }
    val grandTotalClubs: Int = departments.sumOf { it.totalClubs }
    val grandTotalFemales: Int = departments.sumOf { it.totalFemaleAthletes }
    val grandTotalMales: Int = departments.sumOf { it.totalMaleAthletes }
}
