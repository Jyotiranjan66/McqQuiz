package com.demo.mcqquiz.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColors = darkColorScheme(

    primary = BluePrimary,

    secondary = BlueSecondary,

    tertiary = BlueTertiary,

    background = BackgroundDark,

    surface = SurfaceDark,

    surfaceVariant = SurfaceVariantDark,

    error = RedError

)

private val LightColors = lightColorScheme(

    primary = BluePrimary,

    secondary = BlueSecondary,

    tertiary = BlueTertiary,

    background = BackgroundLight,

    surface = SurfaceLight,

    surfaceVariant = SurfaceVariantLight,

    error = RedError

)

@Composable
fun McqQuizTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme = when {

        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {

            val context = LocalContext.current

            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)

        }

        darkTheme -> DarkColors

        else -> LightColors

    }

    val view = LocalView.current

    if (!view.isInEditMode) {

        SideEffect {

            val window = (view.context as Activity).window

            window.statusBarColor = Color.Transparent.toArgb()

            window.navigationBarColor = Color.Transparent.toArgb()

            WindowCompat.getInsetsController(

                window,
                view

            ).apply {

                isAppearanceLightStatusBars = !darkTheme

                isAppearanceLightNavigationBars = !darkTheme

            }

        }

    }

    MaterialTheme(

        colorScheme = colorScheme,

        typography = AppTypography,

        shapes = AppShapes,

        content = content

    )

}