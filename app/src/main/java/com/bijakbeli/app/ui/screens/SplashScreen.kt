package com.bijakbeli.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijakbeli.app.ui.theme.ForestGreen
import com.bijakbeli.app.ui.theme.DarkGreen
import com.bijakbeli.app.ui.theme.GoldHighlight
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val scale = remember { Animatable(0f) }
    
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(ForestGreen, DarkGreen)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "BijakBeli",
                color = GoldHighlight,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Compare & Save",
                color = Color.White,
                fontSize = 18.sp,
                letterSpacing = 2.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            CircularProgressIndicator(
                color = GoldHighlight,
                modifier = Modifier.size(32.dp)
            )
        }
        
        // Simulating the store badges from Figma
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BadgePlaceholder("Lotus's")
            BadgePlaceholder("MYDIN")
            BadgePlaceholder("BP Mall")
        }
    }
}

@Composable
fun BadgePlaceholder(name: String) {
    Surface(
        color = Color.White.copy(alpha = 0.2f),
        shape = androidx.compose.foundation.shape.CircleShape,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

// Helper for Overshoot animation
class OvershootInterpolator(private val tension: Float = 2f) {
    fun getInterpolation(t: Float): Float {
        var tValue = t
        tValue -= 1.0f
        return tValue * tValue * ((tension + 1) * tValue + tension) + 1.0f
    }
}
