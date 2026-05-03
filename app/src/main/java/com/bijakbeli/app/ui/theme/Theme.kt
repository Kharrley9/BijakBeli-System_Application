package com.bijakbeli.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = TealLight,
    onPrimary = Color.White,
    primaryContainer = DarkTeal,
    onPrimaryContainer = TealSurface,
    secondary = CoralLight,
    onSecondary = Color.White,
    secondaryContainer = WarmCoral,
    onSecondaryContainer = CoralSurface,
    tertiary = AccentGold,
    onTertiary = Color.Black,
    background = Color(0xFF0F1A1B),
    onBackground = TextOnDark,
    surface = Color(0xFF152627),
    onSurface = TextOnDark,
    surfaceVariant = Color(0xFF1E3334),
    onSurfaceVariant = Color(0xFFBBC8CA),
    outline = Color(0xFF3A5456),
    error = ErrorRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = DeepTeal,
    onPrimary = Color.White,
    primaryContainer = TealSurface,
    onPrimaryContainer = DarkTeal,
    secondary = WarmCoral,
    onSecondary = Color.White,
    secondaryContainer = CoralSurface,
    onSecondaryContainer = Color(0xFF7A2020),
    tertiary = AccentGold,
    onTertiary = Color.White,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = DividerColor,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun BijakBeliTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to false to maintain brand consistency
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
