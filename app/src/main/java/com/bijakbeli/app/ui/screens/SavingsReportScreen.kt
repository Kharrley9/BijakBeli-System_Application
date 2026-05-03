package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bijakbeli.app.data.repository.MockDataRepository
import com.bijakbeli.app.ui.theme.*
import com.bijakbeli.app.viewmodel.BijakBeliViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingsReportScreen(
    navController: NavController,
    viewModel: BijakBeliViewModel,
    onBackClick: () -> Unit = {}
) {
    val items = viewModel.shoppingList
    val savings = viewModel.getSavingsEstimate()
    val storeRecommendations = viewModel.getCheapestStoreRecommendations()
    val budget = viewModel.monthlyBudget.doubleValue

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Savings Report", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White) },
                navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier.background(Brush.verticalGradient(listOf(GradientHeaderStart, GradientHeaderEnd)))
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).background(BackgroundLight)) {
            // Hero savings
            item {
                Box(modifier = Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(GradientHeaderStart, GradientHeaderEnd)))) {
                    Column(modifier = Modifier.padding(bottom = 36.dp, top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Total Saved this Month", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("RM${String.format("%.2f", savings.potentialSavings)}", color = AccentGold, fontWeight = FontWeight.ExtraBold, fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.1f)) {
                            Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.RocketLaunch, contentDescription = null, tint = AccentGold, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("You're a Smart Buyer!", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            // Dashboard card
            item {
                Surface(modifier = Modifier.padding(horizontal = 20.dp).offset(y = (-20).dp), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 8.dp) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Budget vs Spent", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                        Spacer(modifier = Modifier.height(16.dp))
                        SrBar("Monthly Budget", budget, SurfaceVariant, TextSecondary, 1f)
                        Spacer(modifier = Modifier.height(12.dp))
                        SrBar("Actual Spent", viewModel.getSpentAmount() + savings.totalIfCheapest, DeepTeal, DeepTeal,
                            ((viewModel.getSpentAmount() + savings.totalIfCheapest) / budget).toFloat().coerceIn(0f, 1f))
                    }
                }
            }
            item { Text("Store Performance", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary, modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) }
            items(storeRecommendations) { rec ->
                val isCheapest = rec == storeRecommendations.firstOrNull()
                val sc = when (rec.storeId) { "store_bp" -> BPMallBlue; "store_sq" -> SquareOneOrange; "store_aeon" -> AeonBigRed; else -> DeepTeal }
                SrStoreCard(rec, sc, isCheapest)
            }
            item { Text("Smart Deal Breakdown", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary, modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) }
            items(items.take(3)) { item ->
                val product = MockDataRepository.getProduct(item.productId)
                val cheapest = MockDataRepository.getCheapestPrice(item.productId)
                if (product != null && cheapest != null) { SrDealCard(product, cheapest.first, item.quantity, cheapest.second.name) }
            }
            item {
                Spacer(modifier = Modifier.height(40.dp))
                Button(
                    onClick = { }, modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(56.dp),
                    shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = DeepTeal),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(Icons.Outlined.Share, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Share Savings Report", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun SrBar(label: String, amount: Double, color: Color, textColor: Color, progress: Float) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontSize = 12.sp, color = TextSecondary)
            Text("RM${String.format("%.2f", amount)}", fontSize = 12.sp, color = textColor, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape).background(SurfaceVariant)) {
            Box(modifier = Modifier.fillMaxWidth(progress).fillMaxHeight().clip(CircleShape).background(
                Brush.horizontalGradient(if (color == SurfaceVariant) listOf(DividerColor, DividerColor) else listOf(DeepTeal, TealLight))
            ))
        }
    }
}

@Composable
private fun SrStoreCard(rec: com.bijakbeli.app.data.model.StoreRecommendation, storeColor: Color, isCheapest: Boolean) {
    Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 6.dp), shape = RoundedCornerShape(14.dp), color = Color.White,
        shadowElevation = if (isCheapest) 4.dp else 1.dp) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(40.dp), shape = RoundedCornerShape(10.dp), color = storeColor.copy(alpha = 0.1f)) {
                Box(contentAlignment = Alignment.Center) { Text(rec.storeName.take(1), fontWeight = FontWeight.ExtraBold, color = storeColor) }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(rec.storeName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                Text("${rec.itemsAvailable} items available", fontSize = 11.sp, color = TextSecondary)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("RM${String.format("%.2f", rec.totalCost)}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = if (isCheapest) SuccessGreen else TextPrimary)
                if (isCheapest) Text("Cheapest Option", fontSize = 10.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SrDealCard(product: com.bijakbeli.app.data.model.Product, price: Double, qty: Int, store: String) {
    Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 4.dp), shape = RoundedCornerShape(12.dp), color = Color.White, shadowElevation = 1.dp) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                Text("Qty: $qty • at $store", fontSize = 11.sp, color = TextSecondary)
            }
            Text("RM${String.format("%.2f", price * qty)}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SuccessGreen)
        }
    }
}
