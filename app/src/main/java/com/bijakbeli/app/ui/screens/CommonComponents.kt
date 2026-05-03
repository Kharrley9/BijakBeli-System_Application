package com.bijakbeli.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bijakbeli.app.ui.theme.*

@Composable
fun BottomNavBar(navController: NavController?) {
    if (navController == null) return

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
        modifier = Modifier.shadow(12.dp, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        val items = listOf(
            NavItem("home", "Home", Icons.Outlined.Home, Icons.Filled.Home),
            NavItem("promotions", "Deals", Icons.Outlined.LocalOffer, Icons.Filled.LocalOffer),
            NavItem("shopping_list", "My List", Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart),
            NavItem("savings_report", "Report", Icons.Outlined.Assessment, Icons.Filled.Assessment),
            NavItem("profile", "Profile", Icons.Outlined.Person, Icons.Filled.Person)
        )

        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            if (item.route == "home") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        if (isSelected) item.filledIcon else item.outlinedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label, fontSize = 11.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DeepTeal,
                    selectedTextColor = DeepTeal,
                    indicatorColor = TealSurface,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted
                )
            )
        }
    }
}

private data class NavItem(
    val route: String,
    val label: String,
    val outlinedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val filledIcon: androidx.compose.ui.graphics.vector.ImageVector
)
