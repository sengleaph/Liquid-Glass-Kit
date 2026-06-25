package com.fuu.liquidglass.sample

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.core.GlassStyles

/** Sample-app-only presets. Not part of the public library. */
internal object SampleGlassStyles {

    /** Warm orange glass, good over photo backdrops. */
    val Sunset = GlassStyle(
        frost = 24.dp,
        tint = Color(0xFFFF7A59).copy(alpha = 0.20f),
        edge = 0.9f,
        contrast = 1.10f,
        saturation = 1.30f,
        refraction = 0.70f,
        dispersion = 0.18f,
        curve = 1.20f,
        shape = RoundedCornerShape(28.dp),
    )

    /** Deep, near-black glass for "premium" hero cards. */
    val Obsidian = GlassStyle(
        frost = 30.dp,
        tint = Color(0xFF0B0B14).copy(alpha = 0.32f),
        edge = 0.75f,
        contrast = 1.20f,
        saturation = 0.85f,
        refraction = 0.50f,
        dispersion = 0.05f,
        curve = 1.00f,
        shape = RoundedCornerShape(20.dp),
    )

    /** Derived from the library Liquid preset — just bigger corners. */
    val LiquidXL = GlassStyles.Liquid.copy(shape = RoundedCornerShape(40.dp))

    /** Every sample preset — handy for a “style picker” row in the demo. */
    val All = listOf(
        "Sunset" to Sunset,
        "Obsidian" to Obsidian,
        "Liquid XL" to LiquidXL,
    )
}