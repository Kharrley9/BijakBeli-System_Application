package com.bijakbeli.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bijakbeli.app.ui.theme.BijakBeliTheme
import com.bijakbeli.app.ui.screens.*
import com.bijakbeli.app.viewmodel.BijakBeliViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BijakBeliTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BijakBeliApp()
                }
            }
        }
    }
}

@Composable
fun BijakBeliApp() {
    val navController = rememberNavController()
    val viewModel: BijakBeliViewModel = viewModel()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onSplashFinished = {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterClick = {
                    navController.navigate("home") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
        composable("home") {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable("search") {
            SearchScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onProductClick = { productId ->
                    navController.navigate("product_detail/$productId")
                }
            )
        }
        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: "p1"
            ProductDetailScreen(
                productId = productId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onCompareClick = { navController.navigate("comparison/$productId") },
                onAlternativeClick = { altId ->
                    navController.navigate("product_detail/$altId")
                }
            )
        }
        composable("comparison/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: "p1"
            ComparisonScreen(
                productId = productId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onProductClick = { altId ->
                    navController.navigate("product_detail/$altId")
                }
            )
        }
        composable("shopping_list") {
            ShoppingListScreen(navController = navController, viewModel = viewModel)
        }
        composable("promotions") {
            PromotionsScreen(navController = navController)
        }
        composable("savings_report") {
            SavingsReportScreen(
                navController = navController,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable("profile") {
            ProfileScreen(
                navController = navController,
                viewModel = viewModel,
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
