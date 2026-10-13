package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.DynamicFeed
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Sports
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FibaScoreboardDialog
import com.example.ui.components.ShareAppDialog
import com.example.ui.theme.BasketballOrange
import com.example.ui.theme.CourtCyan
import com.example.ui.theme.FibaNavy
import com.example.ui.theme.OlympicGold
import com.example.ui.viewmodel.AcashViewModel

enum class AppTab(
    val title: String,
    val kreylLabel: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    FEED("Fil", "Fil d'actualité", Icons.Filled.DynamicFeed, Icons.Outlined.DynamicFeed, "tab_feed"),
    SPORTS("Lòt Espò", "⚽ Lòt Espò (5)", Icons.Filled.SportsSoccer, Icons.Outlined.SportsSoccer, "tab_sports"),
    DEPARTMENTS("10 Depatman", "🇭🇹 10 Depatman", Icons.Filled.LocationCity, Icons.Outlined.LocationCity, "tab_departments"),
    DIASPORA("Dyaspora", "🇨🇦 Dyaspora (Josué)", Icons.Filled.Public, Icons.Outlined.Public, "tab_diaspora"),
    COACHING("Coachs", "📋 Coachs 3X3", Icons.Filled.Sports, Icons.Outlined.Sports, "tab_coaching"),
    FIBA("FIBA & Patnè", "⏱️ FIBA & Patnè", Icons.Filled.Timer, Icons.Outlined.Timer, "tab_fiba"),
    PROFILE("Profil", "👤 Mon Profil", Icons.Filled.Person, Icons.Outlined.Person, "tab_profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer(
    viewModel: AcashViewModel,
    onSignOut: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = androidx.compose.runtime.rememberCoroutineScope()
    val credentialManager = remember { androidx.credentials.CredentialManager.create(context) }
    var currentTab by remember { mutableStateOf(AppTab.FEED) }
    var showScoreboardDialog by remember { mutableStateOf(false) }
    var showAthleticDetailsScreen by remember { mutableStateOf(false) }
    var isSearchActive by remember { mutableStateOf(false) }
    var showNotificationsDialog by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }

    val userProfile by viewModel.userProfile.collectAsState()
    val communityUsers by viewModel.communityUsers.collectAsState()
    val filteredPosts by viewModel.filteredPosts.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val coaches by viewModel.coaches.collectAsState()
    val sessions by viewModel.sessions.collectAsState()
    val scoreboardState by viewModel.scoreboard.collectAsState()

    // Handle back button on secondary tabs or screens
    if (showAthleticDetailsScreen) {
        BackHandler {
            showAthleticDetailsScreen = false
        }
    } else if (currentTab != AppTab.FEED) {
        BackHandler {
            currentTab = AppTab.FEED
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(CircleShape)
                                    .background(BasketballOrange),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SportsBasketball,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "ACASH Connect",
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Black,
                                        color = BasketballOrange,
                                        letterSpacing = (-0.5).sp
                                    )
                                )
                                Text(
                                    text = "Plateforme Officiel Academie Sportive d'Haïti ACASH",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Black,
                                        fontSize = 8.5.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    ),
                                    maxLines = 1
                                )
                                Text(
                                    text = "Homologué Comité Olympique International CIO",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 8.sp,
                                        color = BasketballOrange
                                    ),
                                    maxLines = 1
                                )
                            }
                        }
                    },
                    actions = {
                        // Official FIBA Scoreboard button
                        IconButton(
                            onClick = { showScoreboardDialog = true },
                            modifier = Modifier.testTag("action_fiba_scoreboard")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(BasketballOrange.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = "Chrono FIBA 12s",
                                    tint = BasketballOrange,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Share App button
                        IconButton(
                            onClick = { showShareDialog = true },
                            modifier = Modifier.testTag("action_share_app")
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(CourtCyan.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Pataje Aplikasyon an",
                                    tint = CourtCyan,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Search Toggle
                        IconButton(
                            onClick = { isSearchActive = !isSearchActive },
                            modifier = Modifier.testTag("action_search")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Recherche",
                                tint = if (isSearchActive) BasketballOrange else MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Notification Badge
                        IconButton(
                            onClick = { showNotificationsDialog = true },
                            modifier = Modifier.testTag("action_notifications")
                        ) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = BasketballOrange,
                                        contentColor = Color.White
                                    ) {
                                        Text("2")
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Profile Avatar Quick Jump
                        Box(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(CourtCyan)
                                .clickable { currentTab = AppTab.PROFILE }
                                .testTag("action_profile_avatar"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userProfile.fullName.take(1),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                // Search Bar when toggled active
                AnimatedVisibility(visible = isSearchActive) {
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 2.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.setSearchQuery(it) },
                            placeholder = { Text("Rechercher un coach, une tactique 3x3, un athlète...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("search_field"),
                            singleLine = true,
                            shape = RoundedCornerShape(20.dp),
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                        Icon(Icons.Default.Close, contentDescription = "Effacer")
                                    }
                                }
                            }
                        )
                    }
                }

                // Quick-Access Category Navigation Pills (All 7 Destinations Always 1 Tap Away)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(AppTab.values()) { tab ->
                        val isSelected = currentTab == tab
                        Surface(
                            color = if (isSelected) BasketballOrange else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .clickable { currentTab = tab }
                                .testTag("top_chip_${tab.testTag}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = tab.kreylLabel,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            }
        },
        bottomBar = {
            val bottomBarTabs = listOf(
                AppTab.FEED,
                AppTab.SPORTS,
                AppTab.DEPARTMENTS,
                AppTab.DIASPORA,
                AppTab.PROFILE
            )

            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                bottomBarTabs.forEach { tab ->
                    val isSelected = currentTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentTab = tab },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.title
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 11.sp
                                )
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BasketballOrange,
                            selectedTextColor = BasketballOrange,
                            indicatorColor = BasketballOrange.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.testTag(tab.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (showAthleticDetailsScreen) {
                AthleticDetailsScreen(
                    currentProfile = userProfile,
                    onSaveToFirestore = { bloodType, restingBpm, maxBpm, heightCm, weightKg, gender, onComplete ->
                        viewModel.saveAthleticDetails(
                            bloodType = bloodType,
                            restingBpm = restingBpm,
                            maxBpm = maxBpm,
                            heightCm = heightCm,
                            weightKg = weightKg,
                            gender = gender,
                            onComplete = onComplete
                        )
                    },
                    onNavigateBack = { showAthleticDetailsScreen = false }
                )
            } else {
                when (currentTab) {
                    AppTab.FEED -> {
                        FeedScreen(
                            userProfile = userProfile,
                            posts = filteredPosts,
                            selectedCategory = selectedCategory,
                            onCategorySelected = { viewModel.setCategoryFilter(it) },
                            onLikePost = { viewModel.toggleLike(it) },
                            onAddComment = { id, text -> viewModel.addComment(id, text) },
                            onCreatePost = { text, cat, hl, bpm -> viewModel.createPost(text, cat, hl, bpm) },
                            onNavigateToProfile = { currentTab = AppTab.PROFILE },
                            onOpenScoreboard = { showScoreboardDialog = true }
                        )
                    }
                    AppTab.SPORTS -> {
                        MultiSportSelectionScreen(
                            currentProfile = userProfile,
                            onSelectSport = { sport, pos ->
                                viewModel.selectSportDiscipline(sport, pos)
                            }
                        )
                    }
                    AppTab.DEPARTMENTS -> {
                        DepartmentNetworkScreen(
                            onSelectCommuneForProfile = { dept, com ->
                                viewModel.selectDepartmentCommune(dept, com)
                            }
                        )
                    }
                    AppTab.DIASPORA -> {
                        DiasporaAthletesScreen()
                    }
                    AppTab.COACHING -> {
                        CoachingScreen(
                            userProfile = userProfile,
                            coaches = coaches,
                            sessions = sessions,
                            onBookSession = { coach, date, time, obj, notes ->
                                viewModel.bookSession(coach, date, time, obj, notes)
                            }
                        )
                    }
                    AppTab.FIBA -> {
                        Fiba3x3HubScreen(
                            onOpenScoreboard = { showScoreboardDialog = true }
                        )
                    }
                    AppTab.PROFILE -> {
                        ProfileScreen(
                            userProfile = userProfile,
                            onSaveProfile = { viewModel.updateProfile(it) },
                            communityUsers = communityUsers,
                            onUpdateCommunityUser = { viewModel.updateCommunityUser(it) },
                            onSwitchIdentity = { viewModel.switchActiveIdentity(it) },
                            onShareApp = { showShareDialog = true },
                            onSignOut = {
                                com.example.ui.auth.AuthManager.signOut(
                                    context = context,
                                    credentialManager = credentialManager,
                                    onSignOutComplete = onSignOut,
                                    scope = scope
                                )
                            },
                            onOpenAthleticDetails = { showAthleticDetailsScreen = true },
                            onSaveMetrics = { fgPct, twoPtPct, rpg, assists, apg, games, ppg, cb ->
                                viewModel.savePerformanceMetrics(
                                    fgPercentage = fgPct,
                                    twoPointPercentage = twoPtPct,
                                    reboundsPerGame = rpg,
                                    assists = assists,
                                    assistsPerGame = apg,
                                    gamesPlayed = games,
                                    pointsPerGame = ppg,
                                    onComplete = cb
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    // Official FIBA Scoreboard & 12s Shot Clock Modal Dialog
    if (showScoreboardDialog) {
        FibaScoreboardDialog(
            state = scoreboardState,
            onToggleTimer = { viewModel.toggleMatchTimer() },
            onResetShotClock = { viewModel.resetShotClock() },
            onAddPoints = { teamA, points -> viewModel.addPoints(teamA, points) },
            onAddFoul = { teamA -> viewModel.addFoul(teamA) },
            onResetMatch = { viewModel.resetMatch() },
            onDismiss = { showScoreboardDialog = false }
        )
    }

    // Notifications Dialog
    if (showNotificationsDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationsDialog = false },
            title = {
                Text(
                    text = "Notifications Acash Connect",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "🏀 Coach Marcus a commenté votre séance 3x3",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Il y a 10 min • 'Excellent travail sur le tir à 2-points sous 12 secondes !'",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                    }

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "🇭🇹 Rezo 10 Depatman & Dyaspora : Nouvo rapò",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "Il y a 1 h • 2,709 atlèt anrejistre nan 68 komin. Josué Bien Aimé (Kanada) voye nouvo materyèl espòtif.",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showNotificationsDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = BasketballOrange)
                ) {
                    Text("OK")
                }
            }
        )
    }

    // Pataje Aplikasyon an Dialog
    if (showShareDialog) {
        ShareAppDialog(
            onDismiss = { showShareDialog = false }
        )
    }
}
