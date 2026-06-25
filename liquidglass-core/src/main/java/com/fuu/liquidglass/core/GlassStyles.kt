package com.fuu.liquidglass.core

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Curated [GlassStyle] presets that cover the most common Apple-inspired Liquid Glass looks.
 *
 * Use these as drop-in defaults or as a starting point for further customization:
 *
 * ```
 * GlassTheme(style = GlassStyles.Liquid) { /* ... */ }
 *
 * // or tweak a preset:
 * val mine = GlassStyles.Frosted.copy(tint = Color(0x33000000))
 * ```
 */
public object GlassStyles {

    /**
     * Near-invisible glass with light blur and a faint white tint. Ideal for overlaying onto
     * vivid backdrops where you want the underlying scene to dominate.
     */
    public val Clear: GlassStyle = GlassStyle(
        frost = 8.dp,
        tint = Color.White.copy(alpha = 0.04f),
        edge = 0.30f,
        contrast = 1.00f,
        saturation = 1.00f,
        refraction = 0.20f,
        dispersion = 0.04f,
        curve = 0.60f,
        shape = RoundedCornerShape(20.dp),
    )

    /**
     * The Apple Control Center default — heavy blur, soft white wash, gentle rim. A safe choice
     * for general surfaces.
     */
    public val Frosted: GlassStyle = GlassStyle(
        frost = 1.dp,
        tint = Color.White.copy(alpha = 0.18f),
        edge = 0.50f,
        contrast = 1.05f,
        saturation = 1.10f,
        refraction = 0.40f,
        dispersion = 0.08f,
        curve = 1.00f,
        shape = RoundedCornerShape(24.dp),
    )

    /**
     * Sharp-edged, high-clarity glass with strong rim highlights and stronger refraction. Best for
     * hero surfaces where you want the lens effect to be obvious.
     */
    public val Crystal: GlassStyle = GlassStyle(
        frost = 16.dp,
        tint = Color.White.copy(alpha = 0.08f),
        edge = 0.85f,
        contrast = 1.12f,
        saturation = 1.30f,
        refraction = 0.75f,
        dispersion = 0.20f,
        curve = 1.20f,
        shape = RoundedCornerShape(28.dp),
    )

    /**
     * The flagship preset — generous rounding, a cool-blue tint and strong refraction give the
     * "fluid mercury" feel that defines Apple's Liquid Glass demos.
     */
    public val Liquid: GlassStyle = GlassStyle(
        frost = 28.dp,
        tint = Color(0xFF8FB8FF).copy(alpha = 0.22f),
        edge = 0.65f,
        contrast = 1.15f,
        saturation = 1.40f,
        refraction = 0.90f,
        dispersion = 0.25f,
        curve = 1.50f,
        shape = RoundedCornerShape(32.dp),
    )

    /**
     * Hyper-saturated glass with a magenta tint and exaggerated chromatic dispersion — for
     * playful, attention-grabbing accents (think CTA buttons or status pills).
     */
    public val Neon: GlassStyle = GlassStyle(
        frost = 20.dp,
        tint = Color(0xFFFF4DD2).copy(alpha = 0.20f),
        edge = 1.00f,
        contrast = 1.30f,
        saturation = 1.80f,
        refraction = 0.95f,
        dispersion = 0.45f,
        curve = 1.30f,
        shape = RoundedCornerShape(24.dp),
    )

    /**
     * The default style consumers get when no `GlassTheme` wraps their content. Aliased to
     * [Frosted] because it is the most broadly applicable look.
     */
    public val Default: GlassStyle get() = Frosted

    /**
     * All bundled presets, in display order. Useful for previews and demo screens.
     */
    public val All: List<GlassStyle> = listOf(Clear, Frosted, Crystal, Liquid, Neon)
}
