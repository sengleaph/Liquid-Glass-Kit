package com.fuu.liquidglass.core

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

/**
 * A self-contained, immutable description of a Liquid Glass appearance.
 *
 * Every consumer-facing component in LiquidGlassKit (`GlassCard`, `GlassButton`, …) reads its
 * runtime appearance from a [GlassStyle]. Styles are designed to be theme-able: the active style
 * is provided via `LocalGlassStyle` and can be overridden at any subtree by wrapping content in a
 * `GlassTheme` block.
 *
 * The fields map 1:1 to the underlying `LiquidScope` DSL on `Modifier.liquid {}` so consumers
 * never have to interact with the lower-level Liquid API directly.
 *
 * @property frost Blur radius applied to whatever sits behind the surface. Larger values produce
 *  the soft, frosted-pane look Apple uses on Control Center and lock-screen widgets.
 * @property tint Color layered on top of the blurred backdrop. Use low alpha (5–25%) so the
 *  underlying scene still bleeds through.
 * @property edge Strength of the rim highlight that traces the shape outline, in the range
 *  `0f..1f`. Higher values yield a brighter, glassier edge.
 * @property contrast Contrast multiplier applied to the backdrop. `1f` is neutral; values above
 *  `1f` deepen darks and brighten highlights.
 * @property saturation Saturation multiplier applied to the backdrop. `1f` is neutral; values
 *  above `1f` push colors toward Apple's vivid liquid look.
 * @property refraction How strongly the surface bends light at its rim, in the range `0f..1f`.
 *  Higher values exaggerate the lens effect.
 * @property dispersion Chromatic dispersion applied at the rim — splits white light into a soft
 *  rainbow fringe. Use sparingly; `0.1f`–`0.3f` is typical.
 * @property curve Curvature of the simulated glass surface. `1f` reads as a typical lens; higher
 *  values exaggerate the bulge.
 * @property shape The outline used to clip and frame the glass. Liquid Glass leans on generous
 *  corner radii — `RoundedCornerShape(24.dp)` and above feels native.
 */
@Immutable
public data class GlassStyle(
    val frost: Dp,
    val tint: Color,
    val edge: Float,
    val contrast: Float,
    val saturation: Float,
    val refraction: Float,
    val dispersion: Float,
    val curve: Float,
    val shape: Shape,
) {
    public companion object
}
