package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import com.example.ui.components.AffiliatedSchoolsSection
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CommuneAthleteData
import com.example.data.model.DepartmentNetwork
import com.example.data.model.DepartmentNetworkRepository
import com.example.ui.theme.BasketballOrange
import com.example.ui.theme.CourtCyan
import com.example.ui.theme.FibaNavy
import com.example.ui.theme.OlympicGold
import com.example.ui.theme.SuccessGreen

@Composable
fun DepartmentNetworkScreen(
    onSelectCommuneForProfile: (String, String) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val allDepartments = remember { DepartmentNetworkRepository.departments }
    var searchQuery by remember { mutableStateOf("") }
    var selectedDeptId by remember { mutableStateOf<String?>("all") }

    // Filter departments & communes
    val filteredDepartments = remember(searchQuery, selectedDeptId) {
        allDepartments.filter { dept ->
            if (selectedDeptId != null && selectedDeptId != "all" && dept.departmentId != selectedDeptId) {
                false
            } else if (searchQuery.isBlank()) {
                true
            } else {
                dept.departmentName.contains(searchQuery, ignoreCase = true) ||
                        dept.kreylName.contains(searchQuery, ignoreCase = true) ||
                        dept.capitalCity.contains(searchQuery, ignoreCase = true) ||
                        dept.communes.any {
                            it.communeName.contains(searchQuery, ignoreCase = true) ||
                                    it.kreylName.contains(searchQuery, ignoreCase = true)
                        }
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("department_network_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header: 10 Departments & National Report
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = FibaNavy),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = BasketballOrange,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "REZO NASYONAL ACASH • AYITI",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = null,
                            tint = OlympicGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "10 Depatman & Rapò Chifre pa Komin",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Rezo ACASH kouvri tout 10 depatman jewografik Ayiti yo avèk yon kowòdinasyon rejyonal aktif, 142 klèb patnè, ak yon prezans nan 68 komin nan tout peyi a.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.85f),
                            lineHeight = 17.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 4 Stat Metric Badges
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = Color.White.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${DepartmentNetworkRepository.grandTotalAthletes}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = BasketballOrange
                                    )
                                )
                                Text(
                                    text = "Atlèt Total",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        Surface(
                            color = Color.White.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${DepartmentNetworkRepository.grandTotalCommunes}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = OlympicGold
                                    )
                                )
                                Text(
                                    text = "Komin Kouvri",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        Surface(
                            color = Color.White.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "10 / 10",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = CourtCyan
                                    )
                                )
                                Text(
                                    text = "Depatman",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        Surface(
                            color = Color.White.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${DepartmentNetworkRepository.grandTotalClubs}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = SuccessGreen
                                    )
                                )
                                Text(
                                    text = "Klèb Afilye",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Button to Share/Export National Report
                    Button(
                        onClick = {
                            val shareText = """
                                🇭🇹 RAPÒ CHIFRE OFISYÈL REZO ACASH CONNECT
                                ━━━━━━━━━━━━━━━━━━━━━━━━━
                                Total Atlèt nan Peyi a : ${DepartmentNetworkRepository.grandTotalAthletes} atlèt
                                Komin Kouvri : ${DepartmentNetworkRepository.grandTotalCommunes} komin
                                Depatman Kouvri : 10 sou 10 depatman (100%)
                                Klèb & Akademi : ${DepartmentNetworkRepository.grandTotalClubs} klèb
                                Atlèt Gason : ${DepartmentNetworkRepository.grandTotalMales} (62%)
                                Atlèt Fi : ${DepartmentNetworkRepository.grandTotalFemales} (38%)
                                
                                📍 Detay pa Depatman :
                                • Lwès : 797 atlèt (12 komin)
                                • Nò : 261 atlèt (7 komin)
                                • Latibonit : 329 atlèt (7 komin)
                                • Sid : 227 atlèt (7 komin)
                                • Sant : 202 atlèt (6 komin)
                                • Sidès : 202 atlèt (6 komin)
                                • Nòdwès : 188 atlèt (6 komin)
                                • Grandans : 180 atlèt (6 komin)
                                • Nòdès : 169 atlèt (5 komin)
                                • Nip : 154 atlèt (6 komin)
                                
                                Rejwenn Rezo Acash Connect : https://unglobalcompact.org/take-action/action/anti-corruption-call-to-action?hl=en-US
                            """.trimIndent()
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Pataje Rapò Chifre ACASH la"))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BasketballOrange),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Pataje Rapò Chifre Nasyonal la", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Search Bar for Commune or Department
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Chèche yon komin oswa depatman (eg: Dèlma, Okap, Wanament...)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Efase")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_commune_input"),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // Filter Chips for 10 Departments
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    FilterChip(
                        selected = selectedDeptId == "all",
                        onClick = { selectedDeptId = "all" },
                        label = { Text("Tout (10)") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BasketballOrange,
                            selectedLabelColor = Color.White
                        )
                    )
                }

                items(allDepartments) { dept ->
                    FilterChip(
                        selected = selectedDeptId == dept.departmentId,
                        onClick = {
                            selectedDeptId = if (selectedDeptId == dept.departmentId) "all" else dept.departmentId
                        },
                        label = { Text("${dept.kreylName} (${dept.totalAthletes})") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = BasketballOrange,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Section Écoles Fondamental Affilée (6 Établissements Homologués ACASH & CIO)
        item {
            AffiliatedSchoolsSection()
        }

        // Departments & Commune Breakdown Cards
        items(filteredDepartments) { dept ->
            DepartmentNetworkCard(
                department = dept,
                searchFilter = searchQuery,
                onSelectCommune = { commune ->
                    onSelectCommuneForProfile(dept.kreylName, commune.kreylName)
                    Toast.makeText(
                        context,
                        "Komin chwazi : ${commune.kreylName} (${dept.kreylName})",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}

@Composable
fun DepartmentNetworkCard(
    department: DepartmentNetwork,
    searchFilter: String,
    onSelectCommune: (CommuneAthleteData) -> Unit
) {
    var expanded by remember { mutableStateOf(true) }

    val displayedCommunes = remember(department, searchFilter) {
        if (searchFilter.isBlank()) {
            department.communes
        } else {
            department.communes.filter {
                it.communeName.contains(searchFilter, ignoreCase = true) ||
                        it.kreylName.contains(searchFilter, ignoreCase = true) ||
                        it.coordinatorName.contains(searchFilter, ignoreCase = true)
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("dept_card_${department.departmentId}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Name, Capital, Count & Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(CourtCyan.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = BasketballOrange,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Depatman ${department.kreylName} (${department.departmentName})",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        Text(
                            text = "Chèflye : ${department.capitalCity} • ${department.communes.size} komin anrejistre",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Surface(
                    color = BasketballOrange.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${department.totalAthletes} Atlèt",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = BasketballOrange
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = department.highlights,
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 17.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Regional Coordinator Info
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = null,
                            tint = FibaNavy,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Text(
                                text = "Kowòdonatè Rejyonal : ${department.regionalCoordinator}",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = department.regionalOffice,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = department.coordinatorPhone,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            // Expanded Commune Breakdown
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Rapo Chifre Komin yo nan ${department.kreylName} :",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = BasketballOrange
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        displayedCommunes.forEach { commune ->
                            CommuneRowCard(
                                commune = commune,
                                onSelect = { onSelectCommune(commune) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommuneRowCard(
    commune: CommuneAthleteData,
    onSelect: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(BasketballOrange)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${commune.kreylName} (${commune.communeName})",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Surface(
                    color = BasketballOrange,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "${commune.totalAthletes} atlèt",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Sports breakdown chips
            Text(
                text = "🏀 Basket: ${commune.basketballCount}  •  ⚽ Foot: ${commune.footballCount}  •  🏐 Volley: ${commune.volleyballCount}  •  🏃 Atletis: ${commune.athleticsCount}  •  💃 Danse: ${commune.danceCount}",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Fi: ${commune.femaleAthletes} | Gason: ${commune.maleAthletes}  •  ${commune.activeClubs} klèb",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = CourtCyan)
                )
                Text(
                    text = "Kowòdonatè: ${commune.coordinatorName}",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }
        }
    }
}
