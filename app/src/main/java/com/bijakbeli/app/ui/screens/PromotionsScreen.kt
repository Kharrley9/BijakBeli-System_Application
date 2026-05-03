package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bijakbeli.app.data.repository.MockDataRepository
import com.bijakbeli.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionsScreen(navController: NavController) {
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "BP Mall", "Square One", "AEON Big")
    val allPromos = MockDataRepository.getActivePromotions()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Promotions", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White) },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier.background(Brush.verticalGradient(listOf(GradientHeaderStart, GradientHeaderEnd)))
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).background(BackgroundLight)) {
            item {
                Text("Featured Deals", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary, modifier = Modifier.padding(16.dp))
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    item { PromoBanner("Weekly Fresh", "Direct from farm", Icons.Outlined.Eco, listOf(TealSurface, Color(0xFFD4F1F0)), DeepTeal) }
                    item { PromoBanner("Budget Saver", "Save up to 30%", Icons.Outlined.Savings, listOf(GoldLight, Color(0xFFFDE9C4)), AccentGold) }
                }
            }
            item {
                LazyRow(modifier = Modifier.padding(vertical = 16.dp), contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filters) { filter ->
                        val isSelected = selectedFilter == filter
                        Surface(
                            onClick = { selectedFilter = filter }, shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) DeepTeal else Color.White,
                            border = if (isSelected) null else BorderStroke(1.dp, DividerColor),
                            shadowElevation = if (isSelected) 4.dp else 0.dp
                        ) {
                            Text(filter, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                color = if (isSelected) Color.White else TextSecondary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }
            item { Text("Today's Hot Deals", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) }
            items(allPromos) { promo -> PromoItem(promo) }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
private fun PromoBanner(title: String, subtitle: String, icon: ImageVector, bgGrad: List<Color>, accentColor: Color) {
    Surface(modifier = Modifier.width(280.dp).height(120.dp), shape = RoundedCornerShape(16.dp), shadowElevation = 4.dp) {
        Box(modifier = Modifier.background(Brush.horizontalGradient(bgGrad))) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextPrimary)
                    Text(subtitle, fontSize = 13.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(shape = RoundedCornerShape(8.dp), color = DeepTeal) {
                        Text("Shop Now", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                    }
                }
                Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(52.dp))
            }
        }
    }
}

@Composable
private fun PromoItem(promo: com.bijakbeli.app.data.model.PromotionData) {
    val storeColor = when (promo.storeId) { "store_bp" -> BPMallBlue; "store_sq" -> SquareOneOrange; "store_aeon" -> AeonBigRed; else -> DeepTeal }
    Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 4.dp) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(60.dp), shape = RoundedCornerShape(14.dp), color = SurfaceVariant) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Outlined.LocalOffer, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(28.dp)) }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(shape = RoundedCornerShape(4.dp), color = storeColor) {
                        Text(promo.storeId.replace("store_", "").uppercase(), color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                    if (promo.discountPercent != null) {
                        Surface(shape = RoundedCornerShape(4.dp), color = WarmCoral) {
                            Text("-${promo.discountPercent}%", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(promo.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                Text(promo.description, fontSize = 12.sp, color = TextSecondary, maxLines = 1)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("RM12.50", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = DeepTeal)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("RM15.00", fontSize = 12.sp, color = TextMuted, textDecoration = TextDecoration.LineThrough)
                }
            }
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = TextMuted)
        }
    }
}
