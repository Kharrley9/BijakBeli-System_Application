package com.bijakbeli.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijakbeli.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComparisonScreen(productName: String = "Milo ACTIV-GO 1kg") {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compare Prices", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ForestGreen,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Product Info Card
            ProductHeader(productName)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Available at 3 stores:", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = ForestGreen)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ComparisonList()
        }
    }
}

@Composable
fun ProductHeader(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(80.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color.LightGray
        ) {
            // Image Placeholder
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Category: Beverages", color = Color.Gray, fontSize = 14.sp)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = BestPriceGold, modifier = Modifier.size(16.dp))
                Text(" 4.8 (120 reviews)", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ComparisonList() {
    val storeDeals = listOf(
        StoreDeal("Lotus's", 18.90, LotussRed, true),
        StoreDeal("MYDIN", 19.50, MydinBlue, false),
        StoreDeal("BP Mall", 21.00, BPMallGreen, false)
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(storeDeals) { deal ->
            StorePriceCard(deal)
        }
    }
}

data class StoreDeal(val name: String, val price: Double, val color: Color, val isBest: Boolean)

@Composable
fun StorePriceCard(deal: StoreDeal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Surface(
                    color = deal.color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        deal.name,
                        color = deal.color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                if (deal.isBest) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🏆 BEST PRICE", color = BestPriceGold, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
                    }
                } else {
                    Text("In stock", color = Color.Gray, fontSize = 12.sp)
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text("RM ${String.format("%.2f", deal.price)}", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = if (deal.isBest) ForestGreen else TextDark)
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = if (deal.isBest) BestPriceGold else ForestGreen),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.height(32.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Add to List", fontSize = 12.sp)
                }
            }
        }
    }
}
