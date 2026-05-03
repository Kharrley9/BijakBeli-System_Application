package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijakbeli.app.data.model.AlternativeProduct
import com.bijakbeli.app.data.model.PriceRecord
import com.bijakbeli.app.data.repository.MockDataRepository
import com.bijakbeli.app.ui.theme.*
import com.bijakbeli.app.viewmodel.BijakBeliViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComparisonScreen(
    productId: String,
    viewModel: BijakBeliViewModel,
    onBackClick: () -> Unit = {},
    onProductClick: (String) -> Unit = {}
) {
    val product = MockDataRepository.getProduct(productId)
    val prices = MockDataRepository.getPricesForProduct(productId)
    val promos = MockDataRepository.getPromotionsForProduct(productId)
    val alternatives = MockDataRepository.getAlternativeProducts(productId)
    val isInList = viewModel.isInShoppingList(productId)
    val bestPriceRecord = prices.minByOrNull { MockDataRepository.getEffectivePrice(it) }
    val bestEffectivePrice = bestPriceRecord?.let { MockDataRepository.getEffectivePrice(it) } ?: 0.0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Price Comparison", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(BackgroundLight)
        ) {
            item { CmpSpotlight(product, bestEffectivePrice) }
            item {
                Text("Availability & Prices", fontWeight = FontWeight.Bold, fontSize = 16.sp,
                    color = TextPrimary, modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
            }
            items(prices) { record ->
                val store = MockDataRepository.getStore(record.storeId)
                val isBest = record == bestPriceRecord
                val promo = promos.find { it.storeId == record.storeId }
                val sc = when (record.storeId) { "store_bp" -> BPMallBlue; "store_sq" -> SquareOneOrange; "store_aeon" -> AeonBigRed; else -> DeepTeal }
                CmpStoreCard(store?.name ?: "Store", record, isBest, sc, if (promo != null) "${promo.discountPercent}% OFF" else null)
            }
            if (alternatives.isNotEmpty()) {
                item { Text("Similar Products", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary, modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) }
                items(alternatives) { alt -> CmpAltCard(alt) { onProductClick(alt.product.id) } }
            }
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { if (!isInList) viewModel.addToShoppingList(productId) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isInList) TealSurface else DeepTeal),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = if (isInList) 0.dp else 6.dp)
                ) {
                    Icon(if (isInList) Icons.Filled.Check else Icons.Filled.Add, contentDescription = null, tint = if (isInList) DeepTeal else Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isInList) "Added to List" else "Add to My List", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (isInList) DeepTeal else Color.White)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun CmpSpotlight(product: com.bijakbeli.app.data.model.Product?, bestPrice: Double) {
    Surface(modifier = Modifier.fillMaxWidth().padding(20.dp), shape = RoundedCornerShape(20.dp), color = Color.White, shadowElevation = 6.dp) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(80.dp), shape = RoundedCornerShape(16.dp), color = TealSurface) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Outlined.Inventory2, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(36.dp)) }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Surface(shape = RoundedCornerShape(6.dp), color = TealSurface) {
                    Text(product?.category ?: "Grocery", color = DeepTeal, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(product?.name ?: "", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextPrimary)
                Text("${product?.brand} • ${product?.unitSize}", fontSize = 13.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Best: ", fontSize = 12.sp, color = TextSecondary)
                    Text("RM${String.format("%.2f", bestPrice)}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = SuccessGreen)
                }
            }
        }
    }
}

@Composable
private fun CmpStoreCard(storeName: String, record: PriceRecord, isBest: Boolean, storeColor: Color, promoTag: String?) {
    val effectivePrice = MockDataRepository.getEffectivePrice(record)
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp),
        shape = RoundedCornerShape(14.dp), color = Color.White,
        shadowElevation = if (isBest) 6.dp else 2.dp,
        border = if (isBest) androidx.compose.foundation.BorderStroke(1.5.dp, SuccessGreen.copy(alpha = 0.3f)) else null
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(44.dp), shape = RoundedCornerShape(12.dp), color = storeColor.copy(alpha = 0.1f)) {
                Box(contentAlignment = Alignment.Center) { Text(storeName.take(1), fontWeight = FontWeight.Bold, color = storeColor, fontSize = 16.sp) }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(storeName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                    if (isBest) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(shape = RoundedCornerShape(4.dp), color = SuccessGreen) {
                            Text("CHEAPEST", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                        }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = TextMuted, modifier = Modifier.size(12.dp))
                    Text(" 1.2km away", fontSize = 12.sp, color = TextSecondary)
                }
                if (promoTag != null) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                        Icon(Icons.Filled.LocalFireDepartment, contentDescription = null, tint = WarmCoral, modifier = Modifier.size(14.dp))
                        Text(" $promoTag", color = WarmCoral, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("RM${String.format("%.2f", effectivePrice)}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = if (isBest) SuccessGreen else TextPrimary)
                if (record.promoPrice != null) {
                    Text("RM${String.format("%.2f", record.regularPrice)}", fontSize = 12.sp, color = TextMuted, textDecoration = TextDecoration.LineThrough)
                }
            }
        }
    }
}

@Composable
private fun CmpAltCard(alt: AlternativeProduct, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 5.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp), color = Color.White, border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(36.dp), shape = RoundedCornerShape(8.dp), color = SurfaceVariant) {
                Box(contentAlignment = Alignment.Center) { Icon(Icons.Outlined.ShoppingBag, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(18.dp)) }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(alt.product.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                Text("RM${String.format("%.2f", alt.cheapestPrice)} at ${alt.cheapestStore}", fontSize = 11.sp, color = TextSecondary)
            }
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = TextMuted)
        }
    }
}
