package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijakbeli.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Shopping List", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ForestGreen,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Budget Summary Card
            ShoppingBudgetCard()
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // List Items
            ShoppingListContent()
            
            // Bottom Checkout Summary
            CheckoutSummary()
        }
    }
}

@Composable
fun ShoppingBudgetCard() {
    Surface(
        color = ForestGreen,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Estimated Total", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
            Text("RM 142.50", color = GoldHighlight, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                LinearProgressIndicator(
                    progress = 0.18f,
                    modifier = Modifier.weight(1f).height(6.dp),
                    color = GoldHighlight,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("18% of Budget", color = Color.White, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ShoppingListContent() {
    val items = listOf(
        ShoppingItem("Milo 1kg", "Lotus's", 18.90, 2),
        ShoppingItem("Gardenia Bread", "MYDIN", 3.20, 1),
        ShoppingItem("Eggs 30s", "Lotus's", 12.50, 1),
        ShoppingItem("Cooking Oil 5kg", "BP Mall", 28.50, 1)
    )

    LazyColumn(
        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(items) { item ->
            ListItemCard(item)
        }
    }
}

data class ShoppingItem(val name: String, val store: String, val price: Double, val qty: Int)

@Composable
fun ListItemCard(item: ShoppingItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(modifier = Modifier.size(50.dp), shape = RoundedCornerShape(8.dp), color = BackgroundLight) {}
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(item.store, color = ForestGreen, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                Text("RM ${String.format("%.2f", item.price)}", color = Color.Gray, fontSize = 14.sp)
            }
            
            // Quantity Controls
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    onClick = {},
                    shape = RoundedCornerShape(4.dp),
                    color = BackgroundLight,
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("-", fontWeight = FontWeight.Bold)
                    }
                }
                Text("${item.qty}", modifier = Modifier.padding(horizontal = 12.dp), fontWeight = FontWeight.Bold)
                Surface(
                    onClick = {},
                    shape = RoundedCornerShape(4.dp),
                    color = ForestGreen,
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun CheckoutSummary() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp).padding(bottom = 24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Items (5)", color = Color.Gray)
                Text("RM 142.50", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen)
            ) {
                Text("Go to Store Locations", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
