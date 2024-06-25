package com.example.teendoapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BeetRoot80,
    onPrimary = BeetRoot20,
    primaryContainer = BeetRoot30,
    onPrimaryContainer = BeetRoot94,
    inversePrimary = BeetRoot40,
    secondary = DarkBeetroot80,
    onSecondary = DarkBeetroot20,
    secondaryContainer = DarkBeetroot30,
    onSecondaryContainer = DarkBeetroot90,
    tertiary = Violet80,
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = BeetRootGrey30,
    onSurface = BeetRootGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = BeetRootGrey30,
    onSurfaceVariant = BeetRootGrey80,
    outline = BeetRootGrey80
)

private val LightColorScheme = lightColorScheme(
    primary = BeetRoot40,
    onPrimary = Color.White,
    primaryContainer = BeetRoot94,
    onPrimaryContainer = BeetRoot10,
    inversePrimary = BeetRoot80,
    secondary = DarkBeetroot40,
    onSecondary = Color.White,
    secondaryContainer = DarkBeetroot90,
    onSecondaryContainer = DarkBeetroot10,
    tertiary = Violet40,
    onTertiary = Color.White,
    tertiaryContainer = Violet90,
    onTertiaryContainer = Violet10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background =  BeetRootGrey97,
    onBackground = Grey10,
    surface = BeetRootGrey90,
    onSurface = BeetRootGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = BeetRootGrey90,
    onSurfaceVariant = BeetRootGrey30,
    outline = BeetRootGrey50
)

@Composable
fun TeendoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}