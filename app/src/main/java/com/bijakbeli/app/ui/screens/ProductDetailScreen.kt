package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijakbeli.app.data.repository.MockDataRepository
import com.bijakbeli.app.ui.theme.*
import com.bijakbeli.app.viewmodel.BijakBeliViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: String,
    viewModel: BijakBeliViewModel,
    onBackClick: () -> Unit = {},
    onCompareClick: () -> Unit = {},
    onAlternativeClick: (String) -> Unit = {}
) {
    val product = MockDataRepository.getProduct(productId)
    val prices = MockDataRepository.getPricesForProduct(productId)
    val promos = MockDataRepository.getPromotionsForProduct(productId)
    val alternatives = MockDataRepository.getAlternativeProducts(productId)
    val isInList = viewModel.isInShoppingList(productId)
    val cheapest = MockDataRepository.getCheapestPrice(productId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.Share, contentDescription = "Share", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier.background(
                    Brush.verticalGradient(listOf(GradientHeaderStart, GradientHeaderEnd))
                )
            )
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 16.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onCompareClick,
                        modifier = Modifier.weight(0.4f).height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, DeepTeal)
                    ) {
                        Icon(Icons.Outlined.CompareArrows, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Compare", fontWeight = FontWeight.Bold, color = DeepTeal, fontSize = 13.sp)
                    }
                    Button(
                        onClick = { if (!isInList) viewModel.addToShoppingList(productId) },
                        modifier = Modifier.weight(0.6f).height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isInList) TealSurface else DeepTeal
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = if (isInList) 0.dp else 6.dp)
                    ) {
                        Icon(
                            if (isInList) Icons.Filled.Check else Icons.Filled.Add,
                            contentDescription = null,
                            tint = if (isInList) DeepTeal else Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            if (isInList) "In Your List" else "Add to List",
                            fontWeight = FontWeight.Bold,
                            color = if (isInList) DeepTeal else Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundLight)
        ) {
            // Hero Section with gradient
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(listOf(GradientHeaderStart, GradientHeaderEnd))
                        )
                ) {
                    Canvas(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                        drawCircle(
                            color = Color.White.copy(alpha = 0.05f),
                            radius = 100.dp.toPx(),
                            center = Offset(size.width * 0.8f, size.height * 0.3f)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(bottom = 36.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            modifier = Modifier.size(140.dp),
                            shape = RoundedCornerShape(28.dp),
                            color = Color.White.copy(alpha = 0.1f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.15f))
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Outlined.ShoppingBag,
                                    contentDescription = null,
                                    tint = Color.White.copy(alpha = 0.8f),
                                    modifier = Modifier.size(64.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Info Card
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-24).dp),
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(product?.brand ?: "", color = TextSecondary, fontSize = 14.sp)
                                Text(
                                    product?.name ?: "",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 24.sp,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = SurfaceVariant
                                ) {
                                    Text(
                                        product?.category ?: "",
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextSecondary
                                    )
                                }
                            }
                            if (cheapest != null) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Lowest Price", color = SuccessGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Text(
                                        "RM${String.format("%.2f", cheapest.first)}",
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Black,
                                        color = SuccessGreen
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        Divider(color = DividerColor)
                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Description", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                        Text(
                            product?.description ?: "Enjoy fresh and quality products sourced daily for your needs.",
                            color = TextSecondary,
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Store Comparisons
            item {
                Text(
                    "Price Comparison",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextPrimary,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
                )
            }

            items(prices) { record ->
                val store = MockDataRepository.getStore(record.storeId)
                val isLowest = cheapest?.first == MockDataRepository.getEffectivePrice(record)

                val storeColor = when (record.storeId) {
                    "store_bp" -> BPMallBlue
                    "store_sq" -> SquareOneOrange
                    "store_aeon" -> AeonBigRed
                    else -> DeepTeal
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 5.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    shadowElevation = if (isLowest) 4.dp else 1.dp,
                    border = if (isLowest) androidx.compose.foundation.BorderStroke(1.5.dp, SuccessGreen.copy(alpha = 0.3f)) else null
                ) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = storeColor.copy(alpha = 0.1f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    store?.name?.take(1) ?: "",
                                    fontWeight = FontWeight.Bold,
                                    color = storeColor,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(store?.name ?: "", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                            Text("Updated ${record.lastUpdated}", fontSize = 11.sp, color = TextMuted)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "RM${String.format("%.2f", MockDataRepository.getEffectivePrice(record))}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = if (isLowest) SuccessGreen else TextPrimary
                            )
                            if (isLowest) {
                                Surface(shape = RoundedCornerShape(4.dp), color = SuccessGreenLight) {
                                    Text(
                                        "BEST VALUE",
                                        color = SuccessGreen,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Alternatives
            if (alternatives.isNotEmpty()) {
                item {
                    Text(
                        "More Efficient Options",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 12.dp)
                    )
                }
                items(alternatives) { alt ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 5.dp)
                            .clickable { onAlternativeClick(alt.product.id) },
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                modifier = Modifier.size(36.dp),
                                shape = RoundedCornerShape(8.dp),
                                color = SurfaceVariant
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Outlined.ShoppingBag, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(18.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(alt.product.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextPrimary)
                                if (alt.priceDifference > 0) {
                                    Text(
                                        "Save RM${String.format("%.2f", alt.priceDifference)}",
                                        color = SuccessGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null, tint = TextMuted)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}
