package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.UserProfile
import com.example.ui.theme.BasketballOrange
import com.example.ui.theme.CourtCyan
import com.example.ui.theme.OlympicGold
import com.example.ui.theme.SuccessGreen

@Composable
fun EditUserProfileDialog(
    targetUser: UserProfile,
    editorUser: UserProfile,
    onDismiss: () -> Unit,
    onSaveUser: (UserProfile) -> Unit
) {
    var fullName by remember { mutableStateOf(targetUser.fullName) }
    var email by remember { mutableStateOf(targetUser.email) }
    var phone by remember { mutableStateOf(targetUser.phone) }
    var role by remember { mutableStateOf(targetUser.role) }
    var bloodGroup by remember { mutableStateOf(targetUser.bloodGroup) }
    var restingBpm by remember { mutableStateOf(targetUser.restingBpm.toString()) }
    var maxBpm by remember { mutableStateOf(targetUser.maxBpm.toString()) }
    var heightCm by remember { mutableStateOf(targetUser.heightCm.toString()) }
    var weightKg by remember { mutableStateOf(targetUser.weightKg.toString()) }
    var gender by remember { mutableStateOf(targetUser.gender) }
    var primaryPosition by remember { mutableStateOf(targetUser.primaryPosition) }
    var secondaryPosition by remember { mutableStateOf(targetUser.secondaryPosition) }
    var teamOrClub by remember { mutableStateOf(targetUser.teamOrClub) }
    var city by remember { mutableStateOf(targetUser.city) }
    var bio by remember { mutableStateOf(targetUser.bio) }
    var notes by remember { mutableStateOf(targetUser.notes) }

    // Compétences & Expérience
    var shootingSkill by remember { mutableStateOf(targetUser.shootingSkill.toString()) }
    var defenseSkill by remember { mutableStateOf(targetUser.defenseSkill.toString()) }
    var playmakingSkill by remember { mutableStateOf(targetUser.playmakingSkill.toString()) }
    var conditioningSkill by remember { mutableStateOf(targetUser.physicalConditioningSkill.toString()) }
    var tacticalIqSkill by remember { mutableStateOf(targetUser.tacticalIqSkill.toString()) }
    var experienceYears by remember { mutableStateOf(targetUser.experienceYears.toString()) }
    var competitionLevel by remember { mutableStateOf(targetUser.competitionLevel) }
    var pastClubsOrTeams by remember { mutableStateOf(targetUser.pastClubsOrTeams) }
    var achievements by remember { mutableStateOf(targetUser.achievements) }

    // Coach specifics
    var certifications by remember { mutableStateOf(targetUser.certifications) }
    var coachingPhilosophy by remember { mutableStateOf(targetUser.coachingPhilosophy) }
    var hourlyRate by remember { mutableStateOf(targetUser.hourlyRate) }

    val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    val genders = listOf("Masculin", "Féminin", "Autre")
    val roles = listOf("Athlète", "Coach", "Parent", "Supporter", "Super Admin")

    val canChangeRole = editorUser.isSuperAdmin

    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .testTag("edit_user_profile_dialog"),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Modifye Profil : ${targetUser.fullName}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (editorUser.isSuperAdmin) "👑 Dwa Super Admin (Aksè Total)" else if (editorUser.isCoach) "📋 Dwa Antrenè sou Atlèt" else "👤 Modifikasyon Pèsonèl",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (editorUser.isSuperAdmin) OlympicGold else BasketballOrange,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Fèmen")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 4.dp)
            ) {
                // Info Idantite
                Text("Idantite & Kontak", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Non konplè *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email *") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Telefòn") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Seleksyon Wòl (Si se Super Admin)
                if (canChangeRole) {
                    Text("Wòl nan Kominote Acash :", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        roles.forEach { r ->
                            val isSelected = r.equals(role, ignoreCase = true)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (isSelected) BasketballOrange else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { role = r }
                            ) {
                                Text(
                                    text = r,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 6.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(10.dp))

                // Detay Atletik & Sante (BPM, Wotè, Pwa, San, Sèks)
                Text("Detay Atletik & Sante", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = restingBpm,
                        onValueChange = { restingBpm = it },
                        label = { Text("BPM Repos") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = maxBpm,
                        onValueChange = { maxBpm = it },
                        label = { Text("BPM Max") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = heightCm,
                        onValueChange = { heightCm = it },
                        label = { Text("Wotè (cm)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = weightKg,
                        onValueChange = { weightKg = it },
                        label = { Text("Pwa (kg)") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = bloodGroup,
                        onValueChange = { bloodGroup = it },
                        label = { Text("Gwoup San") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = gender,
                        onValueChange = { gender = it },
                        label = { Text("Sèks") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(10.dp))

                // Postes & Basket
                Text("Pòs & Spesyalisasyon Basket", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = primaryPosition,
                        onValueChange = { primaryPosition = it },
                        label = { Text("Pòs Prensipal") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = secondaryPosition,
                        onValueChange = { secondaryPosition = it },
                        label = { Text("Pòs Segondè") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = teamOrClub,
                        onValueChange = { teamOrClub = it },
                        label = { Text("Klib / Ekip") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("Vil") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(10.dp))

                // Nivo Konpetans Basket (1-100)
                Text("Nivo Konpetans Basket (1-100)", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedTextField(
                        value = shootingSkill,
                        onValueChange = { shootingSkill = it },
                        label = { Text("Tir", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = defenseSkill,
                        onValueChange = { defenseSkill = it },
                        label = { Text("Defans", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = playmakingSkill,
                        onValueChange = { playmakingSkill = it },
                        label = { Text("Drib", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = conditioningSkill,
                        onValueChange = { conditioningSkill = it },
                        label = { Text("Fòm", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = tacticalIqSkill,
                        onValueChange = { tacticalIqSkill = it },
                        label = { Text("QI", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(10.dp))

                // Eksperyans & Nòt
                Text("Eksperyans & Nòt Administratif / Antrenè", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = experienceYears,
                        onValueChange = { experienceYears = it },
                        label = { Text("Ane Pratik") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                    OutlinedTextField(
                        value = competitionLevel,
                        onValueChange = { competitionLevel = it },
                        label = { Text("Nivo Konpetisyon") },
                        modifier = Modifier.weight(1.5f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = pastClubsOrTeams,
                    onValueChange = { pastClubsOrTeams = it },
                    label = { Text("Klib pase yo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = achievements,
                    onValueChange = { achievements = it },
                    label = { Text("Palmarèz & Distenksyon") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Nòt Espesyal (Nòt Antrenè / Nòt Admin)") },
                    placeholder = { Text("Nòt sou devlopman atlet, detay paran oswa sipòtè...") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updated = targetUser.copy(
                        fullName = fullName.trim(),
                        email = email.trim(),
                        phone = phone.trim(),
                        role = role,
                        bloodGroup = bloodGroup.trim(),
                        restingBpm = restingBpm.toIntOrNull() ?: targetUser.restingBpm,
                        maxBpm = maxBpm.toIntOrNull() ?: targetUser.maxBpm,
                        heightCm = heightCm.toFloatOrNull() ?: targetUser.heightCm,
                        weightKg = weightKg.toFloatOrNull() ?: targetUser.weightKg,
                        gender = gender,
                        primaryPosition = primaryPosition.trim(),
                        secondaryPosition = secondaryPosition.trim(),
                        positionOrSpecialty = primaryPosition.ifBlank { targetUser.positionOrSpecialty }.trim(),
                        positions = listOf(primaryPosition.trim(), secondaryPosition.trim()).filter { it.isNotBlank() }.distinct(),
                        teamOrClub = teamOrClub.trim(),
                        city = city.trim(),
                        bio = bio.trim(),
                        notes = notes.trim(),
                        shootingSkill = shootingSkill.toIntOrNull() ?: targetUser.shootingSkill,
                        defenseSkill = defenseSkill.toIntOrNull() ?: targetUser.defenseSkill,
                        playmakingSkill = playmakingSkill.toIntOrNull() ?: targetUser.playmakingSkill,
                        physicalConditioningSkill = conditioningSkill.toIntOrNull() ?: targetUser.physicalConditioningSkill,
                        tacticalIqSkill = tacticalIqSkill.toIntOrNull() ?: targetUser.tacticalIqSkill,
                        experienceYears = experienceYears.toIntOrNull() ?: targetUser.experienceYears,
                        competitionLevel = competitionLevel.trim(),
                        pastClubsOrTeams = pastClubsOrTeams.trim(),
                        achievements = achievements.trim()
                    )
                    onSaveUser(updated)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = BasketballOrange),
                modifier = Modifier.testTag("save_user_in_dialog_button")
            ) {
                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Anrejistre Modifikasyon yo", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Anile")
            }
        }
    )
}
