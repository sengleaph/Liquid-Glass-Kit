package com.fuu.liquidglass.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.core.GlassStyles

/**
 * The current [GlassStyle] applied to all LiquidGlassKit components in this composition subtree.
 *
 * Reads use `LocalGlassStyle.current`. Override by wrapping content in a `GlassTheme { … }`. Falls
 * back to [GlassStyles.Default] when no `GlassTheme` has been provided — this is intentional so
 * components stay renderable in @Preview without manual setup.
 */
public val LocalGlassStyle: ProvidableCompositionLocal<GlassStyle> =
    compositionLocalOf { GlassStyles.Default }
