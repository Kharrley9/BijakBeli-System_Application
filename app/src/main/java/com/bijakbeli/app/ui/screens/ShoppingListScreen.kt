package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bijakbeli.app.data.repository.MockDataRepository
import com.bijakbeli.app.ui.theme.*
import com.bijakbeli.app.viewmodel.BijakBeliViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(navController: NavController, viewModel: BijakBeliViewModel) {
    val items = viewModel.shoppingList
    val budget = viewModel.monthlyBudget.doubleValue
    val spent = viewModel.getSpentAmount()
    val remaining = viewModel.getRemainingBudget()
    val estimatedTotal = viewModel.getEstimatedTotal()
    val savings = viewModel.getSavingsEstimate()
    val storeRecommendations = viewModel.getCheapestStoreRecommendations()
    val storeColors = mapOf("store_bp" to BPMallBlue, "store_sq" to SquareOneOrange, "store_aeon" to AeonBigRed)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My List", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary) },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary) } },
                actions = { TextButton(onClick = { }) { Text("Edit", color = DeepTeal, fontWeight = FontWeight.Bold, fontSize = 15.sp) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(BackgroundLight)) {
            SlBudgetBar(budget, spent, remaining)
            LazyColumn(modifier = Modifier.weight(1f), contentPadding = PaddingValues(bottom = 20.dp)) {
                items(items.size) { index ->
                    val item = items[index]
                    val product = MockDataRepository.getProduct(item.productId)
                    val cheapest = MockDataRepository.getCheapestPrice(item.productId)
                    if (product != null) {
                        SlItemCard(product.name, cheapest?.second?.name ?: "—", cheapest?.first ?: 0.0, item.quantity, item.isChecked,
                            { viewModel.toggleChecked(item.productId) }, { newQty -> viewModel.updateQuantity(item.productId, newQty) })
                    }
                }
                item {
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp).clickable { }, verticalAlignment = Alignment.CenterVertically) {
                        Surface(modifier = Modifier.size(28.dp), shape = CircleShape, color = TealSurface) {
                            Box(contentAlignment = Alignment.Center) { Icon(Icons.Filled.Add, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(16.dp)) }
                        }
                        Text("Add item", color = DeepTeal, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(start = 12.dp))
                    }
                }
                item { Divider(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp), color = DividerColor) }
                item {
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Estimated Total:", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                        Text("RM${String.format("%.2f", estimatedTotal)}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = DeepTeal)
                    }
                }
                if (storeRecommendations.isNotEmpty()) {
                    item { Text("Cheapest Store Recommendation", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary, modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) }
                    items(storeRecommendations.size) { index ->
                        val rec = storeRecommendations[index]
                        val color = storeColors[rec.storeId] ?: DeepTeal
                        SlRecCard(rec, color, index == 0)
                    }
                }
                item { SlSavingsCard(savings) }
            }
            Surface(color = Color.White, shadowElevation = 16.dp) {
                Button(
                    onClick = { navController.navigate("savings_report") },
                    modifier = Modifier.fillMaxWidth().padding(20.dp).height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepTeal),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) { Text("Compare Total & Save!", fontSize = 16.sp, fontWeight = FontWeight.Bold) }
            }
        }
    }
}

@Composable
private fun SlBudgetBar(budget: Double, spent: Double, remaining: Double) {
    Surface(modifier = Modifier.fillMaxWidth(), color = Color.White, shadowElevation = 2.dp) {
        Row(modifier = Modifier.padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            SlBudgetCol("Budget", budget, TextPrimary, Modifier.weight(1f))
            Box(modifier = Modifier.width(1.dp).height(24.dp).background(DividerColor))
            SlBudgetCol("Spent", spent, WarmCoral, Modifier.weight(1f))
            Box(modifier = Modifier.width(1.dp).height(24.dp).background(DividerColor))
            SlBudgetCol("Remaining", remaining, SuccessGreen, Modifier.weight(1f))
        }
    }
}

@Composable
private fun SlBudgetCol(label: String, amount: Double, color: Color, modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(label, fontSize = 11.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
        Text("RM${String.format("%.2f", amount)}", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = color)
    }
}

@Composable
private fun SlItemCard(name: String, store: String, price: Double, quantity: Int, isChecked: Boolean, onCheckedChange: () -> Unit, onQuantityChange: (Int) -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp), shape = RoundedCornerShape(14.dp), color = Color.White, shadowElevation = 2.dp) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(24.dp).clickable { onCheckedChange() }, shape = RoundedCornerShape(6.dp),
                color = if (isChecked) DeepTeal else Color.White,
                border = if (isChecked) null else BorderStroke(1.5.dp, DividerColor)
            ) {
                if (isChecked) Box(contentAlignment = Alignment.Center) { Icon(Icons.Filled.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp)) }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = if (isChecked) TextMuted else TextPrimary, textDecoration = if (isChecked) TextDecoration.LineThrough else null)
                Text(store, fontSize = 12.sp, color = TextSecondary)
            }
            Text("RM${String.format("%.2f", price)}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = if (isChecked) TextMuted else TextPrimary, modifier = Modifier.padding(horizontal = 8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { if (quantity > 1) onQuantityChange(quantity - 1) }, modifier = Modifier.size(28.dp)) {
                    Text("−", fontSize = 18.sp, color = TextSecondary)
                }
                Text("$quantity", fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 4.dp))
                IconButton(onClick = { onQuantityChange(quantity + 1) }, modifier = Modifier.size(28.dp).background(TealSurface, CircleShape)) {
                    Text("+", fontSize = 18.sp, color = DeepTeal, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SlRecCard(rec: com.bijakbeli.app.data.model.StoreRecommendation, color: Color, isCheapest: Boolean) {
    Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp), shape = RoundedCornerShape(12.dp),
        color = if (isCheapest) TealSurface.copy(alpha = 0.4f) else Color.White, shadowElevation = if (isCheapest) 4.dp else 1.dp) {
        Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(40.dp), shape = RoundedCornerShape(10.dp), color = color.copy(alpha = 0.1f)) {
                Box(contentAlignment = Alignment.Center) { Text(rec.storeName.take(1), color = color, fontWeight = FontWeight.ExtraBold) }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(rec.storeName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                    if (isCheapest) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(shape = RoundedCornerShape(4.dp), color = SuccessGreen) {
                            Text("BEST", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                        }
                    }
                }
                Text("${rec.itemsAvailable}/${rec.totalItems} items", fontSize = 11.sp, color = TextSecondary)
            }
            Text("RM${String.format("%.2f", rec.totalCost)}", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = if (isCheapest) SuccessGreen else TextPrimary)
        }
    }
}

@Composable
private fun SlSavingsCard(savings: com.bijakbeli.app.data.model.SavingsEstimate) {
    Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp), shape = RoundedCornerShape(16.dp)) {
        Box(modifier = Modifier.background(Brush.horizontalGradient(listOf(GradientHeaderStart, GradientHeaderEnd)))) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Savings, contentDescription = null, tint = AccentGold, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Your Potential Savings", color = AccentGold, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Cheapest total", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                        Text("RM${String.format("%.2f", savings.totalIfCheapest)}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("You save", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                        Text("RM${String.format("%.2f", savings.potentialSavings)}", color = AccentGold, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
