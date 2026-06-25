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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin

/**
 * Continuously bobs the component up and down to suggest weightlessness, mirroring Apple's
 * "floating glass" hero treatments.
 *
 * The motion is a smooth sine wave; the component never visually leaves its layout bounds because
 * only its draw transform is animated. Safe to apply to layout-critical elements.
 *
 * @param amplitude Maximum vertical displacement (each direction).
 * @param durationMillis Time for one full loop. Lower values are jittery; defaults to ~3 seconds.
 * @param enabled If `false`, returns the receiver unchanged.
 */
public fun Modifier.glassFloatAnimation(
    amplitude: Dp = 6.dp,
    durationMillis: Int = 3200,
    enabled: Boolean = true,
): Modifier = if (!enabled) this else composed {
    val transition = rememberInfiniteTransition(label = "glass-float")
    val phase by transition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "glass-float-phase",
    )
    val amplitudePx = with(LocalDensity.current) { amplitude.toPx() }
    graphicsLayer {
        translationY = sin(phase) * amplitudePx
    }
}
