package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BasketballOrange
import com.example.ui.theme.CourtCyan
import com.example.ui.theme.FibaNavy
import com.example.ui.theme.OlympicGold

data class StoryItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val badge: String,
    val statHighlight: String,
    val storyContent: String,
    val gradientColors: List<Color>
)

@Composable
fun FacebookStoriesBar(
    currentUserName: String,
    onAddStoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedStory by remember { mutableStateOf<StoryItem?>(null) }

    val stories = remember {
        listOf(
            StoryItem(
                id = "1",
                name = "Coach Marcus",
                subtitle = "FIBA Pro",
                badge = "Entraîneur FIBA",
                statHighlight = "Shot Clock 12s | 185 BPM",
                storyContent = "Session d'entraînement 3x3 terminée à l'INSEP ! Analyse des tirs à 2-pts en transition rapide. Bravo aux athlètes pour leur intensité défensive.",
                gradientColors = listOf(Color(0xFFFA6400), Color(0xFFD64D00))
            ),
            StoryItem(
                id = "2",
                name = "Sarah T.",
                subtitle = "CIO 3x3",
                badge = "Sélection Olympique",
                statHighlight = "Score: 21-18 | 192 BPM",
                storyContent = "Qualification en poche pour le World Tour 3x3 ! Un match à haute tension décidé au buzzer des 12 secondes. Merci pour tous vos encouragements !",
                gradientColors = listOf(Color(0xFF0F1E36), Color(0xFF1E3A5F))
            ),
            StoryItem(
                id = "3",
                name = "Alexis M.",
                subtitle = "NBA Prep",
                badge = "Coach Spécialiste",
                statHighlight = "Adresse 2-pts : 68%",
                storyContent = "Focus du jour : déclenchement du tir après 'short roll'. En 3x3, 1 tir à 2 points vaut 2x plus qu'un tir intérieur. La précision sous fatigue est reine.",
                gradientColors = listOf(Color(0xFF0284C7), Color(0xFF0369A1))
            ),
            StoryItem(
                id = "4",
                name = "David K.",
                subtitle = "Rebondeur",
                badge = "Athlète Elite",
                statHighlight = "198 cm | 96 kg | O+",
                storyContent = "Bilan biométrique validé sur Acash Connect. Prêt pour le tournoi international avec 100% de condition physique !",
                gradientColors = listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9))
            ),
            StoryItem(
                id = "5",
                name = "Dr. Julien",
                subtitle = "Cardiologue",
                badge = "Médecin du Sport",
                statHighlight = "Cardio 3x3 & Hydratation",
                storyContent = "Rappel à tous les compétiteurs 3x3 : vérifiez votre BPM de récupération après 3 matchs consécutifs. Maintenez une bonne hydratation électrolytique.",
                gradientColors = listOf(Color(0xFF10B981), Color(0xFF059669))
            )
        )
    }

    LazyRow(
        modifier = modifier.testTag("stories_bar"),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // First Story Card: "Créer une Story" (Facebook style)
        item {
            Surface(
                modifier = Modifier
                    .width(105.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onAddStoryClick() }
                    .testTag("create_story_button"),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                shadowElevation = 2.dp
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Upper half
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(BasketballOrange.copy(alpha = 0.35f), BasketballOrange)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SportsBasketball,
                                contentDescription = "Basketball",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        // Lower half
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.7f)
                                .background(MaterialTheme.colorScheme.surface),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Créer une\nStory 3x3",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    lineHeight = 14.sp
                                ),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(top = 14.dp, bottom = 6.dp)
                            )
                        }
                    }

                    // Floating Add Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center)
                            .clip(CircleShape)
                            .background(BasketballOrange)
                            .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Ajouter une story",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        // Other Story Cards
        items(stories, key = { it.id }) { story ->
            Surface(
                modifier = Modifier
                    .width(105.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { selectedStory = story }
                    .testTag("story_item_${story.id}"),
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 3.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(story.gradientColors))
                        .padding(8.dp)
                ) {
                    // Profile ring on top
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.TopStart)
                            .border(2.dp, OlympicGold, CircleShape)
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = story.name.take(1),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                    }

                    // Bottom info
                    Column(
                        modifier = Modifier.align(Alignment.BottomStart)
                    ) {
                        Text(
                            text = story.subtitle,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OlympicGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        )
                        Text(
                            text = story.name,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

    // Modal when a story is clicked
    selectedStory?.let { story ->
        AlertDialog(
            onDismissRequest = { selectedStory = null },
            title = {
                Text(
                    text = "Story 3x3 : ${story.name}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column {
                    Text(
                        text = story.badge,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = BasketballOrange,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = story.statHighlight,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = story.storyContent,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedStory = null },
                    colors = ButtonDefaults.buttonColors(containerColor = BasketballOrange)
                ) {
                    Text("Fermer")
                }
            }
        )
    }
}
