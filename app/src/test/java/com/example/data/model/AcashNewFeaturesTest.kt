package com.example.data.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AcashNewFeaturesTest {

    @Test
    fun testInternationalPartnersContainsUnGlobalCompact() {
        val partners = InternationalPartnersData.partnersList
        assertTrue("Should have international partners", partners.isNotEmpty())

        val unPartner = partners.find { it.id == "un_global_compact_anticorruption" }
        assertNotNull("UN Global Compact Anti-Corruption must be present", unPartner)
        assertEquals("PATNÈ OFISYÈL / PARTENAIRE OFFICIEL", unPartner!!.role)
        assertEquals("https://unglobalcompact.org/take-action/action/anti-corruption-call-to-action?hl=en-US", unPartner.linkUrl)
        assertEquals("PATNÈ OFISYÈL", unPartner.badge)
        assertTrue(unPartner.description.contains("anti-corruption", ignoreCase = true) || unPartner.description.contains("kòripsyon", ignoreCase = true))
    }

    @Test
    fun testMultiSportDisciplinesContainsAllRequestedSports() {
        val sports = MultiSportData.sportsList
        assertEquals(5, sports.size)

        val sportIds = sports.map { it.id }
        assertTrue("Must contain football", sportIds.contains("football"))
        assertTrue("Must contain volleyball", sportIds.contains("volleyball"))
        assertTrue("Must contain tennis", sportIds.contains("tennis"))
        assertTrue("Must contain danse", sportIds.contains("danse"))
        assertTrue("Must contain athletisme", sportIds.contains("athletisme"))

        sports.forEach { sport ->
            assertTrue("${sport.name} must have positions", sport.positions.isNotEmpty())
            assertTrue("${sport.name} must have drills", sport.drills.isNotEmpty())
            assertTrue("${sport.name} must have rules", sport.keyRules.isNotEmpty())
            assertTrue("${sport.name} must have target BPM range", sport.targetBpmRange.isNotBlank())
            assertTrue("${sport.name} must have federation info", sport.federation.isNotBlank())
        }
    }

    @Test
    fun testHaitiTenDepartmentsAndCommunesReport() {
        val departments = DepartmentNetworkRepository.departments
        assertEquals("Must have exactly 10 departments", 10, departments.size)

        val deptIds = departments.map { it.departmentId }
        val expectedDeptIds = listOf("lwes", "no", "nodes", "nodwes", "latibonit", "sant", "sid", "sides", "grandans", "nip")
        expectedDeptIds.forEach { expectedId ->
            assertTrue("Department $expectedId must be present", deptIds.contains(expectedId))
        }

        // Grand total checks
        assertEquals(2709, DepartmentNetworkRepository.grandTotalAthletes)
        assertEquals(68, DepartmentNetworkRepository.grandTotalCommunes)
        assertEquals(157, DepartmentNetworkRepository.grandTotalClubs)
        assertTrue(DepartmentNetworkRepository.grandTotalMales > 0)
        assertTrue(DepartmentNetworkRepository.grandTotalFemales > 0)
        assertEquals(DepartmentNetworkRepository.grandTotalAthletes, DepartmentNetworkRepository.grandTotalMales + DepartmentNetworkRepository.grandTotalFemales)

        // Verify key communes exist
        val lwes = departments.find { it.departmentId == "lwes" }!!
        val portAuPrince = lwes.communes.find { it.kreylName == "Pòtoprens" }
        assertNotNull(portAuPrince)
        assertEquals(145, portAuPrince!!.totalAthletes)

        val delmas = lwes.communes.find { it.kreylName == "Dèlma" }
        assertNotNull(delmas)
        assertEquals(112, delmas!!.totalAthletes)

        val no = departments.find { it.departmentId == "no" }!!
        val okap = no.communes.find { it.kreylName == "Okap" }
        assertNotNull(okap)
        assertEquals(118, okap!!.totalAthletes)
    }

    @Test
    fun testJosueBienAimeCanadaDiasporaAmbassador() {
        val josue = DiasporaRepository.josueBienAime
        assertNotNull(josue)
        assertEquals("Josué Bien Aimé", josue.fullName)
        assertTrue("Country must be Canada", josue.country.contains("Canada") || josue.country.contains("Kanada"))
        assertTrue("City must include Montreal", josue.city.contains("Montréal"))
        assertTrue(josue.isAmbassador)
        assertTrue(josue.keyAchievements.isNotEmpty())
        assertTrue(josue.bio.contains("Josué Bien Aimé"))
        assertEquals("🇨🇦", josue.flagEmoji)

        val otherAthletes = DiasporaRepository.otherDiasporaAthletes
        assertTrue("Must have other diaspora athletes", otherAthletes.isNotEmpty())
    }

    @Test
    fun testUserProfileWithNewSportAndRegionFields() {
        val profile = UserProfile(
            fullName = "Jean Valmy",
            email = "valmy@acash.org",
            selectedSport = "Football",
            selectedDepartment = "Nò",
            selectedCommune = "Okap",
            primaryPosition = "Atakè Pwent"
        )
        assertEquals("Football", profile.selectedSport)
        assertEquals("Nò", profile.selectedDepartment)
        assertEquals("Okap", profile.selectedCommune)
        assertEquals("Atakè Pwent", profile.primaryPosition)
    }
}
