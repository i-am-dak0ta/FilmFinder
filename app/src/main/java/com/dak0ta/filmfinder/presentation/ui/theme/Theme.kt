package com.dak0ta.filmfinder.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkNeutral300,
    secondary = DarkNeutral500,
    background = DarkNeutral200,
    surface = DarkNeutral400,
    onPrimary = DarkNeutral1000,
    onSecondary = DarkNeutral1000,
    onBackground = DarkNeutral1100,
    onSurface = DarkNeutral1100
)

private val LightColorScheme = lightColorScheme(
    primary = Neutral800,
    secondary = Neutral600,
    background = Neutral100,
    surface = Neutral300,
    onPrimary = Neutral100,
    onSecondary = Neutral100,
    onBackground = Neutral1000,
    onSurface = Neutral1000
)

@Composable
fun FilmFinderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}