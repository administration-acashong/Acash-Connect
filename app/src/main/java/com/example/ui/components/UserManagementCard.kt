package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.theme.BasketballOrange
import com.example.ui.theme.CourtCyan
import com.example.ui.theme.FibaNavy
import com.example.ui.theme.OlympicGold
import com.example.ui.theme.SuccessGreen

@Composable
fun UserManagementCard(
    currentUser: UserProfile,
    communityUsers: List<UserProfile>,
    onUpdateUser: (UserProfile) -> Unit,
    onSwitchIdentity: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("Tout") }
    var userToEdit by remember { mutableStateOf<UserProfile?>(null) }
    var showIdentitySwitcher by remember { mutableStateOf(false) }

    val athletes = communityUsers.filter { it.isAthlete }
    val coaches = communityUsers.filter { it.isCoach }
    val parents = communityUsers.filter { it.isParent }
    val supporters = communityUsers.filter { it.isSupporter }

    val filteredList = when (selectedFilter) {
        "Atlèt" -> athletes
        "Antrenè" -> coaches
        "Paran" -> parents
        "Sipòtè" -> supporters
        else -> communityUsers
    }

    if (userToEdit != null) {
        EditUserProfileDialog(
            targetUser = userToEdit!!,
            editorUser = currentUser,
            onDismiss = { userToEdit = null },
            onSaveUser = { updated ->
                onUpdateUser(updated)
                userToEdit = null
            }
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("user_management_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // Header: Wòl Aktyèl & Titre
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .background(
                                if (currentUser.isSuperAdmin) Brush.linearGradient(listOf(OlympicGold, BasketballOrange))
                                else if (currentUser.isCoach) Brush.linearGradient(listOf(CourtCyan, FibaNavy))
                                else Brush.linearGradient(listOf(BasketballOrange, FibaNavy)),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (currentUser.isSuperAdmin) Icons.Default.AdminPanelSettings else if (currentUser.isCoach) Icons.Default.WorkspacePremium else Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (currentUser.isSuperAdmin) "Panèl Super Admin (ADM)" else if (currentUser.isCoach) "Jesyon Atlèt yo (Dwa Antrenè)" else "Dwa & Aksè Profil",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black)
                        )
                        Text(
                            text = "Wòl ou : ${currentUser.role} (${currentUser.fullName})",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (currentUser.isSuperAdmin) OlympicGold else if (currentUser.isCoach) CourtCyan else BasketballOrange,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Bouton pou chanje idantite tès
                OutlinedButton(
                    onClick = { showIdentitySwitcher = !showIdentitySwitcher },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.testTag("switch_identity_button")
                ) {
                    Icon(Icons.Default.SwapHoriz, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (showIdentitySwitcher) "Kache" else "Chanje Wòl", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Identite Switcher Accordion
            AnimatedVisibility(visible = showIdentitySwitcher) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Teste aplikasyon an kòm :",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Super Admin
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (currentUser.isSuperAdmin) OlympicGold else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, OlympicGold),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    val admin = communityUsers.find { it.isSuperAdmin } ?: currentUser
                                    onSwitchIdentity(admin)
                                    showIdentitySwitcher = false
                                }
                                .testTag("select_super_admin_role")
                        ) {
                            Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("👑 ADM", fontWeight = FontWeight.Black, fontSize = 10.sp, color = if (currentUser.isSuperAdmin) Color.Black else OlympicGold)
                                Text("Super Admin", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (currentUser.isSuperAdmin) Color.Black else MaterialTheme.colorScheme.onSurface)
                            }
                        }

                        // Coach
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (currentUser.isCoach) CourtCyan else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, CourtCyan),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    val coach = coaches.firstOrNull() ?: currentUser
                                    onSwitchIdentity(coach)
                                    showIdentitySwitcher = false
                                }
                                .testTag("select_coach_role")
                        ) {
                            Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("📋 COACH", fontWeight = FontWeight.Black, fontSize = 10.sp, color = if (currentUser.isCoach) Color.Black else CourtCyan)
                                Text("Antrenè", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (currentUser.isCoach) Color.Black else MaterialTheme.colorScheme.onSurface)
                            }
                        }

                        // Atlet
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (currentUser.isAthlete && !currentUser.isSuperAdmin) BasketballOrange else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, BasketballOrange),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    val athlete = athletes.firstOrNull() ?: currentUser
                                    onSwitchIdentity(athlete)
                                    showIdentitySwitcher = false
                                }
                                .testTag("select_athlete_role")
                        ) {
                            Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🏀 ATLET", fontWeight = FontWeight.Black, fontSize = 10.sp, color = if (currentUser.isAthlete && !currentUser.isSuperAdmin) Color.White else BasketballOrange)
                                Text("Alexandre", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (currentUser.isAthlete && !currentUser.isSuperAdmin) Color.White else MaterialTheme.colorScheme.onSurface)
                            }
                        }

                        // Paran
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (currentUser.isParent) SuccessGreen else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    val parent = parents.firstOrNull() ?: currentUser
                                    onSwitchIdentity(parent)
                                    showIdentitySwitcher = false
                                }
                                .testTag("select_parent_role")
                        ) {
                            Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("👨‍👩‍👦 PARAN", fontWeight = FontWeight.Black, fontSize = 10.sp, color = if (currentUser.isParent) Color.White else SuccessGreen)
                                Text("P. Dupont", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = if (currentUser.isParent) Color.White else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(10.dp))

            // ============================================================
            // KONT SUPER ADMIN (bassianysma@gmail.com)
            // ============================================================
            if (currentUser.isSuperAdmin) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = OlympicGold.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, OlympicGold),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = OlympicGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "KONT SUPER ADMIN OFISYÈL",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, color = OlympicGold)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Email : ${UserProfile.SUPER_ADMIN_EMAIL}",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.ExtraBold)
                        )
                        Text(
                            text = "✅ Ou gen dwa modifye TOUT enfòmasyon PARAN, ATLET, ANTRENE ak SIPOTE.",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Onglet Filtre pou Super Admin
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf("Tout", "Atlèt", "Antrenè", "Paran", "Sipòtè").forEach { f ->
                        val isSel = selectedFilter == f
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSel) BasketballOrange else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedFilter = f }
                        ) {
                            Text(
                                text = f,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Lis tout itilizatè yo pou Super Admin
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    filteredList.forEach { user ->
                        UserRowItem(
                            user = user,
                            canEdit = true,
                            onEdit = { userToEdit = user }
                        )
                    }
                }
            } else if (currentUser.isCoach) {
                // ============================================================
                // KONT ANTRENE (Dwa modifye ATLÈT yo)
                // ============================================================
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = CourtCyan.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, CourtCyan),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = CourtCyan, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "DWA ANTRENE SOU ATLÈT YO",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black, color = CourtCyan)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "✅ Antrenè a gen dwa modifye tout atlèt li yo (BPM, wotè, pwa, pòs, nivo konpetans, estati, nòt).",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Lis Atlèt nan Ekip la (${athletes.size}) :",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    athletes.forEach { athlete ->
                        UserRowItem(
                            user = athlete,
                            canEdit = true,
                            onEdit = { userToEdit = athlete }
                        )
                    }
                }
            } else {
                // ============================================================
                // KONT ATLET, PARAN oswa SIPOTE
                // ============================================================
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "DWA ITILIZATÈ PÈSONÈL",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SuccessGreen)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Ou gen tout posiblite pou w modifye pwòp profil ou, mete ajou detay atletik ak enfòmasyon kontak ou.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = { userToEdit = currentUser },
                    colors = ButtonDefaults.buttonColors(containerColor = BasketballOrange),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("edit_my_profile_btn")
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Modifye Profil Mwen Kounye a", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun UserRowItem(
    user: UserProfile,
    canEdit: Boolean,
    onEdit: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                user.isSuperAdmin -> OlympicGold
                                user.isCoach -> CourtCyan
                                user.isParent -> SuccessGreen
                                user.isSupporter -> FibaNavy
                                else -> BasketballOrange
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.fullName.take(1),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = user.fullName,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = when {
                                user.isSuperAdmin -> OlympicGold.copy(alpha = 0.2f)
                                user.isCoach -> CourtCyan.copy(alpha = 0.2f)
                                user.isParent -> SuccessGreen.copy(alpha = 0.2f)
                                user.isSupporter -> FibaNavy.copy(alpha = 0.2f)
                                else -> BasketballOrange.copy(alpha = 0.2f)
                            }
                        ) {
                            Text(
                                text = user.role,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp,
                                    color = when {
                                        user.isSuperAdmin -> Color(0xFFB45309)
                                        user.isCoach -> CourtCyan
                                        user.isParent -> SuccessGreen
                                        user.isSupporter -> Color.White
                                        else -> BasketballOrange
                                    }
                                ),
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "${user.positionOrSpecialty} • ${user.city}",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp),
                        maxLines = 1
                    )
                }
            }

            if (canEdit) {
                Button(
                    onClick = onEdit,
                    colors = ButtonDefaults.buttonColors(containerColor = BasketballOrange),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("edit_user_${user.id}_button")
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Modifye", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
