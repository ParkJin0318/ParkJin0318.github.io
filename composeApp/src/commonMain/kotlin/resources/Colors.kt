package resources

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun blogColors() = if (isSystemInDarkTheme()) blogDarkColors() else blogLightColors()

private fun blogLightColors(
    primary: Color = Color(0xFFA1B5D0),
    primaryVariant: Color = Color(0xFF296FCF),
    secondary: Color = Color(0xFF000000),
    secondaryVariant: Color = Color(0xFF000000),
    background: Color = Color(0xFFFFFFFF),
    surface: Color = Color(0xFFFFFFFF),
    error: Color = Color(0xFFF55656),
    onPrimary: Color = Color(0xFFA1B5D0),
    onSecondary: Color = Color(0xFF000000),
    onBackground: Color = Color(0xFFFFFFFF),
    onSurface: Color = Color(0xFFFFFFFF),
    onError: Color = Color(0xFFF55656),
) = lightColors(
    primary = primary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onPrimary = onPrimary,
    onSecondary = onSecondary,
    onBackground = onBackground,
    onSurface = onSurface,
    onError = onError,
)

private fun blogDarkColors(
    secondary: Color = Color(0xFFFFFFFF),
    secondaryVariant: Color = Color(0xFFFFFFFF),
    background: Color = Color(0xFF2B2B2B),
    surface: Color = Color(0xFF2B2B2B),
    onBackground: Color = Color(0xFF2B2B2B),
    onSurface: Color = Color(0xFF2B2B2B)
) = blogLightColors(
    secondary = secondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    onBackground = onBackground,
    onSurface = onSurface,
)
