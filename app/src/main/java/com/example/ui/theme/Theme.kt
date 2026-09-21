package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = RitGoldAccent,
  onPrimary = RitNavyDark,
  primaryContainer = RitCobalt,
  onPrimaryContainer = Color.White,
  secondary = RitTeal,
  onSecondary = Color.White,
  secondaryContainer = DarkSurfaceCard,
  onSecondaryContainer = RitTealLight,
  tertiary = RitGoldAccent,
  background = DarkBackground,
  onBackground = DarkTextPrimary,
  surface = DarkSurface,
  onSurface = DarkTextPrimary,
  surfaceVariant = DarkSurfaceCard,
  onSurfaceVariant = DarkTextSecondary,
  outline = DarkBorder
)

private val LightColorScheme = lightColorScheme(
  primary = RitNavyPrimary,
  onPrimary = Color.White,
  primaryContainer = RitCobalt,
  onPrimaryContainer = Color.White,
  secondary = RitTeal,
  onSecondary = Color.White,
  secondaryContainer = RitTealLight,
  onSecondaryContainer = RitNavyDark,
  tertiary = RitGoldAccent,
  onTertiary = RitNavyDark,
  background = RitSurfaceLight,
  onBackground = RitTextPrimary,
  surface = RitSurfaceCard,
  onSurface = RitTextPrimary,
  surfaceVariant = Color(0xFFF1F5F9),
  onSurfaceVariant = RitTextSecondary,
  outline = RitBorderSubtle
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
