package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bijakbeli.app.data.repository.MockDataRepository
import com.bijakbeli.app.ui.theme.*
import com.bijakbeli.app.viewmodel.BijakBeliViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: BijakBeliViewModel) {
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight)
        ) {
            item { HomeHeader(navController, viewModel) }
            item { PreferredStoresSection() }
            item { TodayBestDeals(navController) }
            item { BudgetOverviewSection(viewModel) }
            item { QuickActionsSection(navController) }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
private fun HomeHeader(navController: NavController, viewModel: BijakBeliViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GradientHeaderStart, GradientHeaderEnd)
                )
            )
    ) {
        // Decorative circles
        Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            drawCircle(
                color = Color.White.copy(alpha = 0.04f),
                radius = 120.dp.toPx(),
                center = Offset(size.width * 0.9f, size.height * 0.1f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.03f),
                radius = 80.dp.toPx(),
                center = Offset(size.width * 0.05f, size.height * 0.8f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 52.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hi ${viewModel.userName.value} \uD83D\uDC4B",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Let's shop smart today!",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Surface(
                    modifier = Modifier.size(44.dp),
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.12f),
                    border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
                    onClick = { }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = Color.White, modifier = Modifier.size(22.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .clickable { navController.navigate("search") }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Search, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Search grocery items...",
                        color = TextMuted,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = TealSurface
                    ) {
                        Icon(
                            Icons.Outlined.Tune,
                            contentDescription = "Filter",
                            tint = DeepTeal,
                            modifier = Modifier.padding(6.dp).size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferredStoresSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            "Preferred Stores",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StoreChip(name = "BP Mall", color = BPMallBlue)
            StoreChip(name = "Square One", color = SquareOneOrange)
            StoreChip(name = "AEON Big", color = AeonBigRed)
        }
    }
}

@Composable
private fun StoreChip(name: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(1.dp, DividerColor),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color, CircleShape)
            )
            Text(name, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }
    }
}

@Composable
private fun TodayBestDeals(navController: NavController) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Today's Best Deals", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
            TextButton(onClick = { navController.navigate("promotions") }) {
                Text("See All", color = DeepTeal, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            item {
                DealCard(
                    store = "AEON BIG",
                    title = "Fresh Items\nWeekly Sale",
                    desc = "Valid till 29 Apr",
                    badge = "10% OFF",
                    icon = Icons.Outlined.Eco,
                    bgGradient = listOf(TealSurface, Color(0xFFD4F1F0)),
                    storeColor = AeonBigRed,
                    badgeColor = WarmCoral
                )
            }
            item {
                DealCard(
                    store = "SQUARE ONE",
                    title = "Muncul Sale\nBeras 10kg",
                    desc = "Was RM29 → RM27.50",
                    badge = "",
                    icon = Icons.Outlined.Inventory2,
                    bgGradient = listOf(GoldLight, Color(0xFFFDE9C4)),
                    storeColor = SquareOneOrange,
                    badgeColor = WarmCoral
                )
            }
            item {
                DealCard(
                    store = "BP MALL",
                    title = "Milo Madness\n1kg Pack",
                    desc = "Was RM18.90 → RM16.90",
                    badge = "HOT",
                    icon = Icons.Outlined.LocalCafe,
                    bgGradient = listOf(CoralSurface, Color(0xFFFFE0E0)),
                    storeColor = BPMallBlue,
                    badgeColor = WarmCoral
                )
            }
        }
    }
}

@Composable
private fun DealCard(
    store: String,
    title: String,
    desc: String,
    badge: String,
    icon: ImageVector,
    bgGradient: List<Color>,
    storeColor: Color,
    badgeColor: Color
) {
    Surface(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier.background(
                Brush.verticalGradient(bgGradient)
            )
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = storeColor
                    ) {
                        Text(
                            store,
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                    if (badge.isNotEmpty()) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = badgeColor
                        ) {
                            Text(
                                badge,
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Icon(
                    icon,
                    contentDescription = null,
                    tint = DeepTeal,
                    modifier = Modifier.size(36.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary, lineHeight = 18.sp)
                Text(desc, color = TextSecondary, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}

@Composable
private fun BudgetOverviewSection(viewModel: BijakBeliViewModel) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
        Text("Budget Overview", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(12.dp))
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Monthly Budget", fontSize = 12.sp, color = TextSecondary)
                        Text(
                            "RM${String.format("%.2f", viewModel.monthlyBudget.doubleValue)}",
                            fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Remaining", fontSize = 12.sp, color = TextSecondary)
                        Text(
                            "RM${String.format("%.2f", viewModel.getRemainingBudget())}",
                            fontWeight = FontWeight.Bold, fontSize = 20.sp, color = SuccessGreen
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                val progress = (viewModel.getSpentAmount() / viewModel.monthlyBudget.doubleValue).toFloat().coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape)
                        .background(SurfaceVariant)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(DeepTeal, TealLight)
                                )
                            )
                    )
                }
                Text(
                    "Spent: RM${String.format("%.2f", viewModel.getSpentAmount())} (${(progress * 100).toInt()}%)",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
private fun QuickActionsSection(navController: NavController) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Quick Actions", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickActionItem(icon = Icons.Outlined.Search, label = "Search", color = DeepTeal) { navController.navigate("search") }
            QuickActionItem(icon = Icons.Outlined.QrCodeScanner, label = "Scan", color = WarmCoral) { /* Scan */ }
            QuickActionItem(icon = Icons.Outlined.List, label = "My List", color = AccentGold) { navController.navigate("shopping_list") }
            QuickActionItem(icon = Icons.Outlined.Star, label = "Promos", color = SuccessGreen) { navController.navigate("promotions") }
        }
    }
}

@Composable
private fun QuickActionItem(icon: ImageVector, label: String, color: Color, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Surface(
            modifier = Modifier.size(60.dp),
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.background(
                    Brush.verticalGradient(
                        listOf(color.copy(alpha = 0.08f), color.copy(alpha = 0.03f))
                    )
                )
            ) {
                Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(26.dp))
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(label, fontSize = 12.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
    }
}
