package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SportsHandball
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.data.model.DiasporaAthlete
import com.example.data.model.DiasporaRepository
import com.example.ui.theme.BasketballOrange
import com.example.ui.theme.CourtCyan
import com.example.ui.theme.FibaNavy
import com.example.ui.theme.OlympicGold
import com.example.ui.theme.SuccessGreen

@Composable
fun DiasporaAthletesScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val josue = remember { DiasporaRepository.josueBienAime }
    val otherAthletes = remember { DiasporaRepository.otherDiasporaAthletes }

    var showJoinDialog by remember { mutableStateOf(false) }
    var diasporaName by remember { mutableStateOf("") }
    var diasporaCountry by remember { mutableStateOf("") }
    var diasporaCity by remember { mutableStateOf("") }
    var diasporaSport by remember { mutableStateOf("") }
    var diasporaEmail by remember { mutableStateOf("") }
    var diasporaMessage by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("diaspora_athletes_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header: Diaspora Network
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
                                text = "DYASPORA ACASH • MONN LAN",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.Flight,
                            contentDescription = null,
                            tint = OlympicGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Rezo Atlèt nan Dyaspora a",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Konekte tout talan ayisyen k ap briye nan Kanada, Etazini, Frans, Repiblik Dominikèn ak lòt kote pou sipòte devlopman espòtif ak jèn atlèt nan 10 depatman peyi d Ayiti.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.85f),
                            lineHeight = 17.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { showJoinDialog = !showJoinDialog },
                        colors = ButtonDefaults.buttonColors(containerColor = BasketballOrange),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (showJoinDialog) "Fèmen Fòm Enskripsyon an" else "Rantre nan Rezo Dyaspora ACASH",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Join Diaspora Form (Collapsible)
        if (showJoinDialog) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Enskripsyon Atlèt oswa Manm Dyaspora :",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = BasketballOrange)
                        )

                        OutlinedTextField(
                            value = diasporaName,
                            onValueChange = { diasporaName = it },
                            label = { Text("Non konplè w") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = diasporaCountry,
                                onValueChange = { diasporaCountry = it },
                                label = { Text("Peyi (eg: Kanada, USA)") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            OutlinedTextField(
                                value = diasporaCity,
                                onValueChange = { diasporaCity = it },
                                label = { Text("Vil (eg: Montréal, Miami)") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }

                        OutlinedTextField(
                            value = diasporaSport,
                            onValueChange = { diasporaSport = it },
                            label = { Text("Espò w (Basket 3x3, Football, Tennis...)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = diasporaEmail,
                            onValueChange = { diasporaEmail = it },
                            label = { Text("Imèl oswa WhatsApp kontak") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        )

                        OutlinedTextField(
                            value = diasporaMessage,
                            onValueChange = { diasporaMessage = it },
                            label = { Text("Kijan w ta renmen kontribye pou Ayiti ?") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 2,
                            shape = RoundedCornerShape(8.dp)
                        )

                        Button(
                            onClick = {
                                if (diasporaName.isNotBlank() && diasporaCountry.isNotBlank()) {
                                    Toast.makeText(
                                        context,
                                        "Mèsi $diasporaName ! Enskripsyon w nan Rezo Dyaspora a anrejistre.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    showJoinDialog = false
                                    diasporaName = ""
                                    diasporaCountry = ""
                                    diasporaCity = ""
                                    diasporaSport = ""
                                    diasporaEmail = ""
                                    diasporaMessage = ""
                                } else {
                                    Toast.makeText(context, "Tanpri ranpli omwen non w ak peyi w.", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Voye Enskripsyon Mwen", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Spotlight: Josué Bien Aimé - Canada (Official Ambassador)
        item {
            JosueBienAimeCard(
                athlete = josue,
                onContact = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:josue.bienaime@acashconnect.org")
                        putExtra(Intent.EXTRA_SUBJECT, "Kontak Rezo ACASH Diaspora Canada - Josué Bien Aimé")
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Kontak: josue.bienaime@acashconnect.org", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

        // Section Title: Lòt Atlèt nan Dyaspora a
        item {
            Text(
                text = "Lòt Atlèt & Kowòdonatè nan Dyaspora Entènasyonal la",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        items(otherAthletes) { athlete ->
            DiasporaAthleteCard(athlete = athlete)
        }
    }
}

@Composable
fun JosueBienAimeCard(
    athlete: DiasporaAthlete,
    onContact: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, OlympicGold, RoundedCornerShape(16.dp))
            .testTag("card_josue_bien_aime_canada"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Ambassador Badge & Country
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = OlympicGold,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = FibaNavy, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "LIDÈ DYASPORA • ANBASADÈ OFISYÈL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Black,
                                color = FibaNavy,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Text(
                    text = "${athlete.flagEmoji} ${athlete.country}",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Profile Header: Avatar & Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(BasketballOrange),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "JB",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = athlete.fullName,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black)
                    )
                    Text(
                        text = athlete.roleOrTitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = BasketballOrange,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "📍 ${athlete.city}  •  ${athlete.clubOrAcademy}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Biography
            Text(
                text = athlete.bio,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Key Achievements Box
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Akonplisman & Inisyativ Josué Bien Aimé nan Kanada :",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = FibaNavy
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    athlete.keyAchievements.forEach { ach ->
                        Text(
                            text = ach,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, lineHeight = 18.sp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Contribution to Haiti
            Surface(
                color = SuccessGreen.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.Top) {
                    Text(text = "🤝 ", fontSize = 14.sp)
                    Column {
                        Text(
                            text = "Enpak sou 10 Depatman Ayiti yo :",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = SuccessGreen)
                        )
                        Text(
                            text = athlete.contributionToHaiti,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, lineHeight = 16.sp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons
            Button(
                onClick = onContact,
                colors = ButtonDefaults.buttonColors(containerColor = FibaNavy),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Kontakte Josué Bien Aimé (Kowòdinasyon Canada)", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DiasporaAthleteCard(athlete: DiasporaAthlete) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Text(text = athlete.flagEmoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = athlete.fullName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${athlete.city} (${athlete.country}) • ${athlete.sport}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = athlete.bio,
                style = MaterialTheme.typography.bodySmall.copy(lineHeight = 17.sp)
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Sipò pou Ayiti : ${athlete.contributionToHaiti}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = BasketballOrange,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Kontak : ${athlete.contactOrSocial}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}
