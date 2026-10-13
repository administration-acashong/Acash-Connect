package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.Wc
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.theme.BasketballOrange
import com.example.ui.theme.CourtCyan
import com.example.ui.theme.FibaNavy
import com.example.ui.theme.OlympicGold
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import com.example.ui.components.AthleteCoachSkillsCard
import com.example.ui.components.PerformanceMetricsTrackerCard
import com.example.ui.components.UserManagementCard
import com.example.ui.theme.PulseRed
import com.example.ui.theme.SuccessGreen

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onSaveProfile: (UserProfile) -> Unit,
    communityUsers: List<UserProfile> = emptyList(),
    onUpdateCommunityUser: (UserProfile) -> Unit = {},
    onSwitchIdentity: (UserProfile) -> Unit = {},
    onShareApp: () -> Unit = {},
    onSignOut: () -> Unit = {},
    onOpenAthleticDetails: () -> Unit = {},
    onSaveMetrics: ((Float, Float, Float, Int, Float, Int, Float, (Boolean, String) -> Unit) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var saveSuccessMessage by remember { mutableStateOf<String?>(null) }

    // Form editing states
    var fullName by remember(userProfile) { mutableStateOf(userProfile.fullName) }
    var email by remember(userProfile) { mutableStateOf(userProfile.email) }
    var phone by remember(userProfile) { mutableStateOf(userProfile.phone) }
    var bloodGroup by remember(userProfile) { mutableStateOf(userProfile.bloodGroup) }
    var restingBpm by remember(userProfile) { mutableStateOf(userProfile.restingBpm.toString()) }
    var maxBpm by remember(userProfile) { mutableStateOf(userProfile.maxBpm.toString()) }
    var heightCm by remember(userProfile) { mutableStateOf(userProfile.heightCm.toString()) }
    var weightKg by remember(userProfile) { mutableStateOf(userProfile.weightKg.toString()) }
    var gender by remember(userProfile) { mutableStateOf(userProfile.gender) }
    var role by remember(userProfile) { mutableStateOf(userProfile.role) }
    var specialty by remember(userProfile) { mutableStateOf(userProfile.positionOrSpecialty) }
    var teamOrClub by remember(userProfile) { mutableStateOf(userProfile.teamOrClub) }
    var bio by remember(userProfile) { mutableStateOf(userProfile.bio) }
    var city by remember(userProfile) { mutableStateOf(userProfile.city) }

    // Postes, compétences et expérience basketball
    var primaryPosition by remember(userProfile) { mutableStateOf(userProfile.primaryPosition) }
    var secondaryPosition by remember(userProfile) { mutableStateOf(userProfile.secondaryPosition) }
    var skillLevel by remember(userProfile) { mutableStateOf(userProfile.skillLevel) }
    var shootingSkill by remember(userProfile) { mutableStateOf(userProfile.shootingSkill.toString()) }
    var defenseSkill by remember(userProfile) { mutableStateOf(userProfile.defenseSkill.toString()) }
    var playmakingSkill by remember(userProfile) { mutableStateOf(userProfile.playmakingSkill.toString()) }
    var conditioningSkill by remember(userProfile) { mutableStateOf(userProfile.physicalConditioningSkill.toString()) }
    var tacticalIqSkill by remember(userProfile) { mutableStateOf(userProfile.tacticalIqSkill.toString()) }
    var experienceYears by remember(userProfile) { mutableStateOf(userProfile.experienceYears.toString()) }
    var competitionLevel by remember(userProfile) { mutableStateOf(userProfile.competitionLevel) }
    var pastClubsOrTeams by remember(userProfile) { mutableStateOf(userProfile.pastClubsOrTeams) }
    var achievements by remember(userProfile) { mutableStateOf(userProfile.achievements) }
    var certifications by remember(userProfile) { mutableStateOf(userProfile.certifications) }
    var coachingPhilosophy by remember(userProfile) { mutableStateOf(userProfile.coachingPhilosophy) }
    var hourlyRate by remember(userProfile) { mutableStateOf(userProfile.hourlyRate) }

    val bloodGroups = listOf("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    val genders = listOf("Masculin", "Féminin", "Autre")
    val roles = listOf("Athlète", "Coach")
    val skillLevels = listOf(
        "Débutant",
        "Intermédiaire",
        "Confirmé Régional",
        "Élite / FIBA World Tour",
        "Professionnel / International"
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("profile_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Success notification banner
        AnimatedVisibility(visible = saveSuccessMessage != null) {
            Surface(
                color = SuccessGreen.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, SuccessGreen),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = saveSuccessMessage ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SuccessGreen
                        )
                    )
                }
            }
        }

        // Header Profile Card (Facebook / FIBA Athlete Banner)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Cover background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(FibaNavy, BasketballOrange, OlympicGold)
                            )
                        )
                )

                // Avatar and Identity
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    // Overlapping Avatar
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .align(Alignment.TopStart)
                            .padding(top = 0.dp)
                            .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                            .clip(CircleShape)
                            .background(BasketballOrange),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userProfile.fullName.take(1),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                    }

                    // Edit button
                    Button(
                        onClick = {
                            isEditing = !isEditing
                            saveSuccessMessage = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isEditing) MaterialTheme.colorScheme.surfaceVariant else BasketballOrange
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .testTag("toggle_edit_profile_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = if (isEditing) MaterialTheme.colorScheme.onSurface else Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isEditing) "Annuler" else "Créer / Modifier",
                            fontWeight = FontWeight.Bold,
                            color = if (isEditing) MaterialTheme.colorScheme.onSurface else Color.White,
                            fontSize = 12.sp
                        )
                    }
                }

                // Profile Names & Badges
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = userProfile.fullName,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (userProfile.role == "Coach") CourtCyan.copy(alpha = 0.2f) else BasketballOrange.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "🏆 ${userProfile.role} Acash",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (userProfile.role == "Coach") CourtCyan else BasketballOrange
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Text(
                            text = userProfile.positionOrSpecialty,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    if (userProfile.teamOrClub.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "🏀 Club : ${userProfile.teamOrClub} (${userProfile.city})",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }

                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = OlympicGold.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "⚡ Espò : ${userProfile.selectedSport}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = FibaNavy
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SuccessGreen.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "🇭🇹 ${userProfile.selectedCommune} (${userProfile.selectedDepartment})",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    if (userProfile.bio.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = userProfile.bio,
                            style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 19.sp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // VIEW MODE: Medical & Biometric Card (Requested fields)
        if (!isEditing) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Passeport Médical & Biométrie FIBA 3X3",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )

                Surface(
                    color = BasketballOrange.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .clickable { onOpenAthleticDetails() }
                        .testTag("open_athletic_details_screen_btn")
                ) {
                    Text(
                        text = "Éditeur Biométrie ➔",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BasketballOrange
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    // Biometrics Grid with requested fields
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Blood group
                        BiometricTile(
                            icon = Icons.Default.Bloodtype,
                            label = "Groupe Sanguin",
                            value = userProfile.bloodGroup,
                            tintColor = PulseRed,
                            modifier = Modifier.weight(1f)
                        )
                        // BPM Repos
                        BiometricTile(
                            icon = Icons.Default.Favorite,
                            label = "BPM Repos",
                            value = "${userProfile.restingBpm} bpm",
                            tintColor = PulseRed,
                            modifier = Modifier.weight(1f)
                        )
                        // BPM Max
                        BiometricTile(
                            icon = Icons.Default.Favorite,
                            label = "BPM Max 3x3",
                            value = "${userProfile.maxBpm} bpm",
                            tintColor = BasketballOrange,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Hauteur / Taille
                        BiometricTile(
                            icon = Icons.Default.Height,
                            label = "Hauteur",
                            value = "${userProfile.heightCm.toInt()} cm",
                            tintColor = CourtCyan,
                            modifier = Modifier.weight(1f)
                        )
                        // Poids
                        BiometricTile(
                            icon = Icons.Default.Scale,
                            label = "Poids",
                            value = "${userProfile.weightKg} kg",
                            tintColor = CourtCyan,
                            modifier = Modifier.weight(1f)
                        )
                        // Sexe
                        BiometricTile(
                            icon = Icons.Default.Wc,
                            label = "Sexe",
                            value = userProfile.gender,
                            tintColor = OlympicGold,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Contact info (Email & Phone requested)
                    Text(
                        text = "Coordonnées de Contact & Licence",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = BasketballOrange,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = userProfile.email,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = SuccessGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = userProfile.phone,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Calculated BMI & 3x3 Intensity Analysis
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "IMC (Indice de Masse Corporelle) :",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = String.format("%.1f", userProfile.bmi),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Black,
                                        color = BasketballOrange
                                    )
                                )
                            }
                            Text(
                                text = "Statut : ${userProfile.bmiCategory}",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Zone Cible Cardio 3X3 (85-95%) :",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = userProfile.targetZone3x3Bpm,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Black,
                                        color = PulseRed
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3X3 BASKETBALL PERFORMANCE METRICS TRACKER & DISPLAY (FIRESTORE)
            PerformanceMetricsTrackerCard(
                userProfile = userProfile,
                onSaveMetrics = { fgPct, twoPtPct, rpg, assists, apg, games, ppg, onComplete ->
                    if (onSaveMetrics != null) {
                        onSaveMetrics(fgPct, twoPtPct, rpg, assists, apg, games, ppg, onComplete)
                    } else {
                        onComplete(true, "Mise à jour effectuée")
                    }
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // POSTES, COMPÉTENCES & EXPÉRIENCE BASKETBALL (ATHLÈTE / COACH)
            AthleteCoachSkillsCard(userProfile = userProfile)

            Spacer(modifier = Modifier.height(14.dp))

            // JESYON PWOFIL AK DWA (SUPER ADMIN & ANTRENE)
            UserManagementCard(
                currentUser = userProfile,
                communityUsers = communityUsers,
                onUpdateUser = onUpdateCommunityUser,
                onSwitchIdentity = onSwitchIdentity
            )

            Spacer(modifier = Modifier.height(14.dp))

            // KAT PATAJE APLIKASYON AN
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = BasketballOrange.copy(alpha = 0.12f),
                border = androidx.compose.foundation.BorderStroke(1.dp, BasketballOrange.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShareApp() }
                    .testTag("profile_share_app_card")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .background(BasketballOrange, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Pataje Acash Connect", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Text("Voye lyen aplikasyon an bay lòt moun", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                        }
                    }
                    Icon(Icons.Default.ArrowForward, contentDescription = null, tint = BasketballOrange)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Shortcut: Edit Profile Button
            Button(
                onClick = { isEditing = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("edit_profile_call_to_action"),
                colors = ButtonDefaults.buttonColors(containerColor = BasketballOrange),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Modifier ou Mettre à jour mon Profil", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Sign out button
            OutlinedButton(
                onClick = onSignOut,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("sign_out_button"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PulseRed)
            ) {
                Text("Se déconnecter de la session", fontWeight = FontWeight.SemiBold)
            }
        } else {
            // EDIT / CREATE PROFILE FORM
            Text(
                text = "Formulaire de Profil & Santé 3X3",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Full Name
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Nom complet") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_fullname"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )

                    // Email (Requested)
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email *") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_email"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )

                    // Telephone (Requested)
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Téléphone *") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_phone"),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )

                    // Groupe Sanguin (Requested)
                    Text(
                        text = "Groupe Sanguin *",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        bloodGroups.take(4).forEach { bg ->
                            val isSelected = bg == bloodGroup
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { bloodGroup = bg }
                                    .testTag("blood_group_$bg"),
                                color = if (isSelected) PulseRed else MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = bg,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        bloodGroups.drop(4).forEach { bg ->
                            val isSelected = bg == bloodGroup
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { bloodGroup = bg }
                                    .testTag("blood_group_$bg"),
                                color = if (isSelected) PulseRed else MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = bg,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }

                    // BPM (Requested)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = restingBpm,
                            onValueChange = { restingBpm = it },
                            label = { Text("BPM Repos *") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_resting_bpm"),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                        OutlinedTextField(
                            value = maxBpm,
                            onValueChange = { maxBpm = it },
                            label = { Text("BPM Max *") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_max_bpm"),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                    }

                    // Hauteur & Poids (Requested)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = heightCm,
                            onValueChange = { heightCm = it },
                            label = { Text("Hauteur (cm) *") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_height"),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                        OutlinedTextField(
                            value = weightKg,
                            onValueChange = { weightKg = it },
                            label = { Text("Poids (kg) *") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_weight"),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                    }

                    // Sexe (Requested)
                    Text(
                        text = "Sexe *",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        genders.forEach { g ->
                            val isSelected = g == gender
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { gender = g }
                                    .testTag("gender_$g"),
                                color = if (isSelected) OlympicGold else MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = g,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }

                    // Rôle (Athlète ou Coach)
                    Text(
                        text = "Rôle sur la plateforme :",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        roles.forEach { r ->
                            val isSelected = r == role
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { role = r }
                                    .testTag("role_$r"),
                                color = if (isSelected) BasketballOrange else MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = r,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )
                            }
                        }
                    }

                    // Spécialité 3x3 / Position
                    OutlinedTextField(
                        value = specialty,
                        onValueChange = { specialty = it },
                        label = { Text("Spécialité 3X3 / Poste") },
                        placeholder = { Text("Ex: Shooter 2-pts / Meneur rapide") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )

                    // Club / Équipe
                    OutlinedTextField(
                        value = teamOrClub,
                        onValueChange = { teamOrClub = it },
                        label = { Text("Club ou Équipe 3x3") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )

                    // Bio
                    OutlinedTextField(
                        value = bio,
                        onValueChange = { bio = it },
                        label = { Text("Bio sportive / Objectifs") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(10.dp))

                    // ==========================================
                    // ÉDITION DES POSTES & SPÉCIALITÉS
                    // ==========================================
                    Text(
                        text = "🏀 Postes & Spécialisations",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BasketballOrange)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = primaryPosition,
                            onValueChange = { primaryPosition = it },
                            label = { Text("Poste Principal *") },
                            placeholder = { Text("Ex: Shooter 2-pts / Meneur") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_primary_position"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                        OutlinedTextField(
                            value = secondaryPosition,
                            onValueChange = { secondaryPosition = it },
                            label = { Text("Poste Secondaire") },
                            placeholder = { Text("Ex: Ailier Polyvalent") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_secondary_position"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(10.dp))

                    // ==========================================
                    // ÉDITION DES COMPÉTENCES (SKILL LEVELS 1-100)
                    // ==========================================
                    Text(
                        text = "⭐ Niveaux de Compétences Basketball (1-100)",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = OlympicGold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Niveau Global du Profil :",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        skillLevels.take(3).forEach { lvl ->
                            val isSelected = lvl == skillLevel
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) OlympicGold else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { skillLevel = lvl }
                            ) {
                                Text(
                                    text = lvl,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface,
                                        fontSize = 10.sp
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        skillLevels.drop(3).forEach { lvl ->
                            val isSelected = lvl == skillLevel
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) OlympicGold else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { skillLevel = lvl }
                            ) {
                                Text(
                                    text = lvl,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface,
                                        fontSize = 10.sp
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = shootingSkill,
                            onValueChange = { shootingSkill = it },
                            label = { Text("Tir / Shoot") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_shooting_skill"),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                        OutlinedTextField(
                            value = defenseSkill,
                            onValueChange = { defenseSkill = it },
                            label = { Text("Défense 1v1") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_defense_skill"),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                        OutlinedTextField(
                            value = playmakingSkill,
                            onValueChange = { playmakingSkill = it },
                            label = { Text("Dribble / Passes") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_playmaking_skill"),
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
                            value = conditioningSkill,
                            onValueChange = { conditioningSkill = it },
                            label = { Text("Cardio & Physique") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_conditioning_skill"),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                        OutlinedTextField(
                            value = tacticalIqSkill,
                            onValueChange = { tacticalIqSkill = it },
                            label = { Text("QI Basket 12s") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_tactical_iq_skill"),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(10.dp))

                    // ==========================================
                    // ÉDITION DE L'EXPÉRIENCE BASKETBALL
                    // ==========================================
                    Text(
                        text = "🏆 Expérience & Historique Basket",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = CourtCyan)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = experienceYears,
                            onValueChange = { experienceYears = it },
                            label = { Text("Années Pratique *") },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("input_exp_years"),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                        OutlinedTextField(
                            value = competitionLevel,
                            onValueChange = { competitionLevel = it },
                            label = { Text("Niveau Compétition") },
                            placeholder = { Text("Ex: FIBA 3x3 World Tour") },
                            modifier = Modifier
                                .weight(1.5f)
                                .testTag("input_comp_level"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = pastClubsOrTeams,
                        onValueChange = { pastClubsOrTeams = it },
                        label = { Text("Clubs & Équipes Passés") },
                        placeholder = { Text("Ex: Paris 3x3 Hoops, Cergy Pontoise") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_past_clubs"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = achievements,
                        onValueChange = { achievements = it },
                        label = { Text("Palmarès & Distinctions (Titres, MVP)") },
                        placeholder = { Text("Ex: Vainqueur Open Plus FIBA 3x3 Paris 2024") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_achievements"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                    )

                    // ==========================================
                    // ÉDITION SPÉCIFIQUE AU RÔLE COACH
                    // ==========================================
                    if (role == "Coach") {
                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "📋 Données Spécifiques Coach Acash",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = SuccessGreen)
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = certifications,
                            onValueChange = { certifications = it },
                            label = { Text("Diplômes & Certifications") },
                            placeholder = { Text("Ex: FIBA 3x3 Coach License, FFBB") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_coach_certifications"),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = coachingPhilosophy,
                                onValueChange = { coachingPhilosophy = it },
                                label = { Text("Philosophie Tactique") },
                                placeholder = { Text("Ex: Jeu rapide 12s, tir intensif") },
                                modifier = Modifier
                                    .weight(1.5f)
                                    .testTag("input_coach_philosophy"),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                            )

                            OutlinedTextField(
                                value = hourlyRate,
                                onValueChange = { hourlyRate = it },
                                label = { Text("Tarif / Séance") },
                                placeholder = { Text("Ex: 50 € / séance") },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("input_coach_rate"),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BasketballOrange)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Action buttons: Enregistrer & Annuler
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { isEditing = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Annuler")
                        }

                        Button(
                            onClick = {
                                val parsedRestingBpm = restingBpm.toIntOrNull() ?: 65
                                val parsedMaxBpm = maxBpm.toIntOrNull() ?: 190
                                val parsedHeight = heightCm.toFloatOrNull() ?: 190f
                                val parsedWeight = weightKg.toFloatOrNull() ?: 85f

                                val parsedShooting = shootingSkill.toIntOrNull() ?: 85
                                val parsedDefense = defenseSkill.toIntOrNull() ?: 75
                                val parsedPlaymaking = playmakingSkill.toIntOrNull() ?: 80
                                val parsedConditioning = conditioningSkill.toIntOrNull() ?: 85
                                val parsedTacticalIq = tacticalIqSkill.toIntOrNull() ?: 80
                                val parsedExpYears = experienceYears.toIntOrNull() ?: 5

                                val updated = userProfile.copy(
                                    fullName = fullName.trim(),
                                    email = email.trim(),
                                    phone = phone.trim(),
                                    bloodGroup = bloodGroup,
                                    restingBpm = parsedRestingBpm,
                                    maxBpm = parsedMaxBpm,
                                    heightCm = parsedHeight,
                                    weightKg = parsedWeight,
                                    gender = gender,
                                    role = role,
                                    positionOrSpecialty = primaryPosition.ifBlank { specialty }.trim(),
                                    teamOrClub = teamOrClub.trim(),
                                    bio = bio.trim(),
                                    city = city.trim(),
                                    primaryPosition = primaryPosition.trim(),
                                    secondaryPosition = secondaryPosition.trim(),
                                    positions = listOf(primaryPosition.trim(), secondaryPosition.trim()).filter { it.isNotBlank() }.distinct(),
                                    skillLevel = skillLevel,
                                    shootingSkill = parsedShooting,
                                    defenseSkill = parsedDefense,
                                    playmakingSkill = parsedPlaymaking,
                                    physicalConditioningSkill = parsedConditioning,
                                    tacticalIqSkill = parsedTacticalIq,
                                    experienceYears = parsedExpYears,
                                    competitionLevel = competitionLevel.trim(),
                                    pastClubsOrTeams = pastClubsOrTeams.trim(),
                                    achievements = achievements.trim(),
                                    certifications = certifications.trim(),
                                    coachingPhilosophy = coachingPhilosophy.trim(),
                                    hourlyRate = hourlyRate.trim()
                                )

                                onSaveProfile(updated)
                                isEditing = false
                                saveSuccessMessage = "Profil enregistré avec succès !"
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BasketballOrange),
                            modifier = Modifier
                                .weight(1.5f)
                                .testTag("save_profile_button")
                        ) {
                            Text("Enregistrer mon Profil", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BiometricTile(
    icon: ImageVector,
    label: String,
    value: String,
    tintColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tintColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
