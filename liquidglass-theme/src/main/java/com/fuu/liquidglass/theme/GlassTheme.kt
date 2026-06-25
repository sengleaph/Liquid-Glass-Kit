package com.fuu.liquidglass.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.core.GlassStyles

/**
 * Provides a [GlassStyle] to every LiquidGlassKit component in [content].
 *
 * `GlassTheme` is a thin wrapper around [MaterialTheme]: it forwards the supplied [colorScheme],
 * [typography] and [shapes] to Material 3, then installs the chosen Liquid Glass [style] via
 * `LocalGlassStyle`. Nest `GlassTheme` calls to scope a different look to a subtree.
 *
 * ```
 * GlassTheme(style = GlassStyles.Liquid) {
 *     GlassCard { /* ... */ }
 *
 *     // Override just this section:
 *     GlassTheme(style = GlassStyles.Neon) {
 *         GlassButton(text = "Buy", onClick = {})
 *     }
 * }
 * ```
 *
 * @param style The Liquid Glass appearance applied to descendants.
 * @param colorScheme Material 3 color scheme. Defaults follow the platform's dark-mode setting.
 * @param typography Material 3 typography. Pass-through to [MaterialTheme].
 * @param shapes Material 3 shapes. Pass-through to [MaterialTheme].
 * @param useDarkTheme Whether to use the dark Material color scheme by default. Ignored when an
 *  explicit [colorScheme] is provided.
 * @param content The themed content.
 */
@Composable
public fun GlassTheme(
    style: GlassStyle = GlassStyles.Default,
    colorScheme: ColorScheme? = null,
    typography: Typography = MaterialTheme.typography,
    shapes: Shapes = MaterialTheme.shapes,
    useDarkTheme: Boolean = isSystemInDarkThemeSafe(),
    content: @Composable () -> Unit,
) {
    val resolvedScheme = colorScheme ?: if (useDarkTheme) DefaultDarkColors else DefaultLightColors
    MaterialTheme(
        colorScheme = resolvedScheme,
        typography = typography,
        shapes = shapes,
    ) {
        CompositionLocalProvider(LocalGlassStyle provides style) {
            content()
        }
    }
}

/**
 * Helper that mirrors `isSystemInDarkTheme()` but keeps `GlassTheme` callable from non-Composable
 * code paths if ever needed in the future. Today it simply delegates.
 */
@Composable
private fun isSystemInDarkThemeSafe(): Boolean =
    androidx.compose.foundation.isSystemInDarkTheme()

private val DefaultLightColors: ColorScheme = lightColorScheme()
private val DefaultDarkColors: ColorScheme = darkColorScheme()

/** Convenience accessor mirroring `MaterialTheme.colorScheme` for symmetry with Material. */
public object GlassThemeDefaults {
    /** A subtle on-glass body text style. Use for primary text drawn over a glass surface. */
    public val onGlassBodyTextStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyLarge
}
