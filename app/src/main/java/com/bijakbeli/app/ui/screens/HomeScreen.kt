package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijakbeli.app.ui.theme.*

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = { HomeBottomBar() }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            
            // Budget Overview Card
            item { BudgetOverviewCard() }
            
            item { SectionHeader("Best Deals Today", "See All") }
            
            // Best Deals Carousel
            item { BestDealsCarousel() }
            
            item { SectionHeader("Shop by Category", null) }
            
            // Categories Grid (Simulated with rows)
            item {
                CategoryGrid()
            }
            
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun HomeTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(ForestGreen, ForestGreen.copy(alpha = 0.8f))
                )
            )
            .padding(16.dp)
            .padding(top = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Hello, Khali!", color = GoldHighlight, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Ready to save money today?", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            }
            Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Search Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Search groceries, brands...", color = Color.Gray)
            }
        }
    }
}

@Composable
fun BudgetOverviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkGreen)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Monthly Budget", color = Color.White, fontSize = 16.sp)
                Text("RM 450 / RM 800", color = GoldHighlight, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = 0.56f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = GoldHighlight,
                trackColor = Color.White.copy(alpha = 0.2f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("You've saved RM 42.50 this week!", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        }
    }
}

@Composable
fun SectionHeader(title: String, action: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = ForestGreen)
        if (action != null) {
            Text(action, color = ForestGreen, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun BestDealsCarousel() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(3) { index ->
            DealCard(index)
        }
    }
}

@Composable
fun DealCard(index: Int) {
    Card(
        modifier = Modifier.width(160.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.LightGray) // Placeholder for product image
            ) {
                Surface(
                    color = BestPriceGold,
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("-25%", modifier = Modifier.padding(horizontal = 4.dp), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Fresh Chicken", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("RM 8.90 /kg", color = ForestGreen, fontWeight = FontWeight.Bold)
                Text("was RM 12.00", color = Color.Gray, fontSize = 10.sp)
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Surface(
                    color = LotussRed.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("at Lotus's", color = LotussRed, fontSize = 10.sp, modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}

@Composable
fun CategoryGrid() {
    val categories = listOf("Fresh", "Dairy", "Bakery", "Frozen", "Pantry", "Drinks")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CategoryItem(categories[0], Icons.Default.AccountCircle, Modifier.weight(1f))
            CategoryItem(categories[1], Icons.Default.AccountBox, Modifier.weight(1f))
            CategoryItem(categories[2], Icons.Default.AddCircle, Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CategoryItem(categories[3], Icons.Default.Call, Modifier.weight(1f))
            CategoryItem(categories[4], Icons.Default.CheckCircle, Modifier.weight(1f))
            CategoryItem(categories[5], Icons.Default.Face, Modifier.weight(1f))
        }
    }
}

@Composable
fun CategoryItem(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = BackgroundLight,
        onClick = { /* Handle category click */ }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = ForestGreen)
            Spacer(modifier = Modifier.height(4.dp))
            Text(name, fontSize = 12.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun HomeBottomBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Search, contentDescription = null) },
            label = { Text("Compare") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
            label = { Text("Cart") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") }
        )
    }
}
