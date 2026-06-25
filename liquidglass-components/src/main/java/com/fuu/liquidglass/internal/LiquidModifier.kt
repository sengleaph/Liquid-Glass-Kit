package com.fuu.liquidglass.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.theme.LocalGlassStyle
import com.fuu.liquidglass.theme.currentLiquidStateOrFallback
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

/**
 * Internal bridge between [GlassStyle] and the upstream Liquid DSL.
 *
 * Components in `com.fuu.liquidglass.components` call [glassEffect] to apply the active style
 * without having to re-state the long property list every time.
 */
internal fun Modifier.applyGlassStyle(state: LiquidState, style: GlassStyle): Modifier =
    this.liquid(state) {
        frost = style.frost
        tint = style.tint
        edge = style.edge
        contrast = style.contrast
        saturation = style.saturation
        refraction = style.refraction
        dispersion = style.dispersion
        curve = style.curve
        shape = style.shape
    }

/**
 * Applies the Liquid Glass visual effect using the supplied [style], or — when none is given —
 * the style currently provided by `LocalGlassStyle`.
 *
 * The shared [LiquidState] is resolved from `LocalLiquidState` so all sibling components
 * participate in the same refraction scene. When no `LiquidRoot` is in scope a private state is
 * remembered automatically; preview-friendly but not visually identical to a full scene.
 */
@Composable
internal fun Modifier.glassEffect(style: GlassStyle? = null): Modifier {
    val resolved = style ?: LocalGlassStyle.current
    val state = currentLiquidStateOrFallback()
    return this.applyGlassStyle(state, resolved)
}
