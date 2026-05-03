package com.bijakbeli.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bijakbeli.app.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val scale = remember { Animatable(0.6f) }
    val alpha = remember { Animatable(0f) }
    val taglineAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            scale.animateTo(1f, tween(1000, easing = FastOutSlowInEasing))
        }
        launch {
            alpha.animateTo(1f, tween(800))
        }
        launch {
            delay(600)
            taglineAlpha.animateTo(1f, tween(600))
        }
        delay(2600)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GradientSplashStart, GradientSplashEnd)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Animated background circles
        SplashBackgroundDecoration()

        // Center Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        ) {
            // Logo with pulsating ring
            Box(contentAlignment = Alignment.Center) {
                PulsatingRing()
                Surface(
                    modifier = Modifier.size(110.dp),
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.12f),
                    border = androidx.compose.foundation.BorderStroke(
                        2.dp, Color.White.copy(alpha = 0.25f)
                    )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Rounded.ShoppingCart,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(52.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                "BijakBeli",
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Shop Smarter • Save Better",
                color = Color.White.copy(alpha = 0.65f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.5.sp,
                modifier = Modifier.alpha(taglineAlpha.value)
            )
        }

        // Bottom Loading
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
                .alpha(alpha.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingDots()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Getting everything ready...",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 12.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
private fun PulsatingRing() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val ringScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = "ringScale"
    )
    val ringAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = "ringAlpha"
    )

    Canvas(modifier = Modifier.size(140.dp)) {
        drawCircle(
            color = Color.White.copy(alpha = ringAlpha),
            radius = size.minDimension / 2 * ringScale,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

@Composable
private fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    val dotScales = (0..2).map { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.4f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, delayMillis = index * 200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ), label = "dot$index"
        )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        dotScales.forEach { scaleState ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .scale(scaleState.value)
                    .background(AccentGold, CircleShape)
            )
        }
    }
}

@Composable
private fun SplashBackgroundDecoration() {
    val infiniteTransition = rememberInfiniteTransition(label = "bg")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "offset"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Large subtle circle top-right
        drawCircle(
            color = Color.White.copy(alpha = 0.03f),
            radius = 220.dp.toPx(),
            center = Offset(size.width * 0.85f, size.height * 0.15f + offset)
        )
        // Medium circle bottom-left
        drawCircle(
            color = Color.White.copy(alpha = 0.04f),
            radius = 160.dp.toPx(),
            center = Offset(size.width * 0.1f, size.height * 0.8f - offset)
        )
        // Small circle mid-left
        drawCircle(
            color = Color.White.copy(alpha = 0.02f),
            radius = 80.dp.toPx(),
            center = Offset(size.width * 0.25f, size.height * 0.45f + offset * 0.5f)
        )
    }
}
