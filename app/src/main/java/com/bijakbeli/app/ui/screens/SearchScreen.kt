package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijakbeli.app.data.model.Product
import com.bijakbeli.app.data.repository.MockDataRepository
import com.bijakbeli.app.ui.theme.*
import com.bijakbeli.app.viewmodel.BijakBeliViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: BijakBeliViewModel,
    onBackClick: () -> Unit = {},
    onProductClick: (String) -> Unit = {}
) {
    val searchQuery = viewModel.searchQuery.value
    val selectedCategory = viewModel.selectedCategory.value
    val results = viewModel.getSearchResults()
    val categories = MockDataRepository.categories

    val categoryIcons: Map<String, ImageVector> = mapOf(
        "Rice" to Icons.Outlined.RiceBowl,
        "Cooking Oil" to Icons.Outlined.WaterDrop,
        "Dairy" to Icons.Outlined.LocalDrink,
        "Bakery" to Icons.Outlined.BakeryDining,
        "Eggs" to Icons.Outlined.Egg,
        "Sugar" to Icons.Outlined.Cookie,
        "Beverages" to Icons.Outlined.LocalCafe,
        "Flour" to Icons.Outlined.Kitchen
    )

    val categoryColors: Map<String, Color> = mapOf(
        "Rice" to Color(0xFF8B5CF6),
        "Cooking Oil" to Color(0xFFF97316),
        "Dairy" to Color(0xFF3B82F6),
        "Bakery" to Color(0xFFEAB308),
        "Eggs" to Color(0xFFF59E0B),
        "Sugar" to Color(0xFFEC4899),
        "Beverages" to Color(0xFF0D7377),
        "Flour" to Color(0xFF6B7280)
    )

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 8.dp, end = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                    Text("Find Groceries", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = TextPrimary)
                }

                Surface(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = SurfaceVariant,
                    shadowElevation = 2.dp
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { viewModel.searchQuery.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search items, brands, stores...", color = TextMuted) },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = DeepTeal) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { viewModel.searchQuery.value = "" }) {
                                    Icon(Icons.Filled.Close, contentDescription = null, tint = TextSecondary)
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(BackgroundLight)
        ) {
            item {
                Text("Categories", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 12.dp))
                LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(categories) { category ->
                        val icon = categoryIcons[category] ?: Icons.Outlined.ShoppingCart
                        val color = categoryColors[category] ?: DeepTeal
                        CategoryChipItem(
                            label = category, icon = icon, color = color,
                            isSelected = selectedCategory == category,
                            onClick = { viewModel.selectedCategory.value = if (selectedCategory == category) null else category }
                        )
                    }
                }
            }
            if (searchQuery.isEmpty() && selectedCategory == null) {
                item {
                    Text("Trending This Week", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary,
                        modifier = Modifier.padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp))
                }
                items(MockDataRepository.products.take(4)) { product ->
                    TrendingResultItem(product, onProductClick, viewModel)
                }
            } else {
                item {
                    Text("Found ${results.size} items", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = TextSecondary,
                        modifier = Modifier.padding(20.dp))
                }
                items(results) { product ->
                    val cheapest = MockDataRepository.getCheapestPrice(product.id)
                    val isInList = viewModel.isInShoppingList(product.id)
                    SearchResultCard(product, cheapest?.first ?: 0.0, cheapest?.second?.name ?: "—", isInList,
                        { onProductClick(product.id) }, { viewModel.addToShoppingList(product.id) })
                }
            }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun CategoryChipItem(label: String, icon: ImageVector, color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Surface(modifier = Modifier.size(64.dp), shape = RoundedCornerShape(18.dp),
            color = if (isSelected) color else Color.White,
            border = if (isSelected) null else BorderStroke(1.dp, DividerColor),
            shadowElevation = if (isSelected) 6.dp else 1.dp) {
            Box(contentAlignment = Alignment.Center,
                modifier = if (!isSelected) Modifier.background(Brush.verticalGradient(listOf(color.copy(alpha = 0.06f), Color.Transparent))) else Modifier
            ) {
                Icon(icon, contentDescription = label, tint = if (isSelected) Color.White else color, modifier = Modifier.size(28.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium, color = if (isSelected) color else TextSecondary)
    }
}

@Composable
private fun TrendingResultItem(product: Product, onProductClick: (String) -> Unit, viewModel: BijakBeliViewModel) {
    Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 6.dp), shape = RoundedCornerShape(14.dp), color = Color.White, shadowElevation = 2.dp) {
        Row(modifier = Modifier.padding(12.dp).clickable { onProductClick(product.id) }, verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(48.dp), shape = RoundedCornerShape(12.dp), color = TealSurface) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.ShoppingCart, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(24.dp))
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                Text(product.brand, fontSize = 12.sp, color = TextSecondary)
            }
            IconButton(onClick = { viewModel.addToShoppingList(product.id) }) {
                Icon(Icons.Outlined.AddCircle, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(28.dp))
            }
        }
    }
}

@Composable
private fun SearchResultCard(product: Product, price: Double, store: String, isInList: Boolean, onClick: () -> Unit, onAdd: () -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp), shape = RoundedCornerShape(16.dp), color = Color.White, shadowElevation = 4.dp) {
        Row(modifier = Modifier.padding(16.dp).clickable { onClick() }, verticalAlignment = Alignment.CenterVertically) {
            Surface(modifier = Modifier.size(56.dp), shape = RoundedCornerShape(14.dp), color = SurfaceVariant) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.ShoppingCart, contentDescription = null, tint = DeepTeal, modifier = Modifier.size(28.dp))
                }
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
                Text("${product.brand} • ${product.unitSize}", fontSize = 12.sp, color = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("RM${String.format("%.2f", price)}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = DeepTeal)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("at $store", fontSize = 11.sp, color = TextSecondary)
                }
            }
            Surface(modifier = Modifier.size(36.dp), shape = CircleShape, color = if (isInList) TealSurface else DeepTeal, onClick = onAdd) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(if (isInList) Icons.Filled.Check else Icons.Filled.Add, contentDescription = null, tint = if (isInList) DeepTeal else Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}
