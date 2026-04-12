package com.bijakbeli.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bijakbeli.app.ui.theme.BijakBeliTheme
import com.bijakbeli.app.ui.screens.SplashScreen
import com.bijakbeli.app.ui.screens.LoginScreen
import com.bijakbeli.app.ui.screens.HomeScreen
import com.bijakbeli.app.ui.screens.ComparisonScreen
import com.bijakbeli.app.ui.screens.ShoppingListScreen

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
                onRegisterClick = { /* navController.navigate("register") */ }
            )
        }
        composable("home") {
            HomeScreen()
        }
        composable("comparison") {
            ComparisonScreen()
        }
        composable("shopping_list") {
            ShoppingListScreen()
        }
    }
}
