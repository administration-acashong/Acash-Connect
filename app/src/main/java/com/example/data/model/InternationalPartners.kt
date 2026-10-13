package com.example.data.model

data class InternationalPartner(
    val id: String,
    val name: String,
    val organization: String,
    val role: String,
    val description: String,
    val linkUrl: String,
    val badge: String,
    val category: String
)

object InternationalPartnersData {
    val partnersList = listOf(
        InternationalPartner(
            id = "un_global_compact_anticorruption",
            name = "Pakt Mondyal Nasyonzini - Apèl Aksyon Kont Kòripsyon",
            organization = "Nations Unies / UN Global Compact",
            role = "PATNÈ OFISYÈL / PARTENAIRE OFFICIEL",
            description = "Inisyativ mondyal Pakt Nasyonzini pou goumen kont kòripsyon, ranfòse etik, transparans, bon gouvènans ak jwèt pwòp (fair-play) nan espò ak devlopman jèn yo.",
            linkUrl = "https://unglobalcompact.org/take-action/action/anti-corruption-call-to-action?hl=en-US",
            badge = "PATNÈ OFISYÈL",
            category = "Etik, Entegrite & Transparans Entènasyonal"
        ),
        InternationalPartner(
            id = "fiba_3x3_official",
            name = "FIBA 3X3 World Tour",
            organization = "Fédération Internationale de Basketball (FIBA)",
            role = "Enstans Règleman & Konpetisyon Entènasyonal",
            description = "Fédération mondiale régissant le basket 3x3 olympique, les circuits pro World Tour et le système mondial de ranking des athlètes.",
            linkUrl = "https://fiba3x3.com",
            badge = "FIBA World Tour",
            category = "Gouvènans Espòtif"
        ),
        InternationalPartner(
            id = "cio_olympic",
            name = "Comité International Olympique (CIO)",
            organization = "Mouvement Olympique International",
            role = "Homologué Comité Olympique International CIO",
            description = "Homologation officielle et promotion des valeurs olympiques : Excellence, Respect, Amitié. Le basket 3x3 et les disciplines ACASH respectent rigoureusement les chartes et règles du CIO.",
            linkUrl = "https://olympics.com",
            badge = "Homologué CIO",
            category = "Mouvmant Olenpik"
        ),
        InternationalPartner(
            id = "nba_cares_jr_nba",
            name = "Jr. NBA & NBA Cares",
            organization = "National Basketball Association",
            role = "Devlopman Jèn & Aksè Espòtif",
            description = "Programmes d'encadrement des compétences fondamentales, formation des entraîneurs et dons de matériel pour la jeunesse.",
            linkUrl = "https://jr.nba.com",
            badge = "Jr. NBA Global",
            category = "Devlopman Jèn"
        ),
        InternationalPartner(
            id = "fhb_haiti",
            name = "Fédération Haïtienne de Basketball (FHB)",
            organization = "FHB Haïti",
            role = "Patnè Nasyonal & Seleksyon Ayiti",
            description = "Kòdinasyon nasyonal pou seleksyon ayisyen yo, tounwa nasyonal ak deteksyon jèn talan nan 10 depatman yo.",
            linkUrl = "https://fiba3x3.com",
            badge = "FHB Haïti",
            category = "Federasyon Nasyonal"
        )
    )
}
