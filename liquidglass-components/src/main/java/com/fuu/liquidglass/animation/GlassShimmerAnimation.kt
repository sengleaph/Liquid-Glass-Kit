package com.fuu.liquidglass.animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Sweeps a soft highlight band across the surface, evoking a glint catching the glass.
 *
 * Implemented as a translucent diagonal gradient drawn *on top* of normal content — it never
 * touches the underlying Liquid refraction, so it composes safely with `glassEffect`. Use for
 * loading or skeleton states, or as a one-off "shine" on a hero card.
 *
 * @param durationMillis Time for one full sweep. Faster = more energetic.
 * @param highlightAlpha Peak alpha of the shimmer band. Keep low (0.08–0.18) to avoid washing out
 *  the underlying tint.
 * @param enabled If `false`, returns the receiver unchanged.
 */
public fun Modifier.glassShimmerAnimation(
    durationMillis: Int = 1800,
    highlightAlpha: Float = 0.12f,
    enabled: Boolean = true,
): Modifier = if (!enabled) this else composed {
    val transition = rememberInfiniteTransition(label = "glass-shimmer")
    val progress by transition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "glass-shimmer-progress",
    )
    drawWithContent {
        drawContent()
        drawShimmer(progress = progress, highlightAlpha = highlightAlpha)
    }
}

private fun DrawScope.drawShimmer(progress: Float, highlightAlpha: Float) {
    val width = size.width
    val height = size.height
    val bandWidth = width * 0.5f
    val start = Offset(x = progress * width - bandWidth, y = 0f)
    val end = Offset(x = progress * width + bandWidth, y = height)
    val brush = Brush.linearGradient(
        colors = listOf(
            Color.Transparent,
            Color.White.copy(alpha = highlightAlpha),
            Color.Transparent,
        ),
        start = start,
        end = end,
    )
    drawRect(brush = brush)
}
