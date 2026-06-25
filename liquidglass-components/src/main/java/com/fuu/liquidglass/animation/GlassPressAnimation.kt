package com.fuu.liquidglass.animation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale

/**
 * Pulses the component slightly smaller while it is being pressed.
 *
 * Pair with an [InteractionSource] from `Modifier.clickable(...)` so the animation tracks the
 * actual gesture. The default `pressedScale` of `0.96` matches Apple's tactile feel.
 *
 * @param interactionSource The source whose `Press` interactions drive the animation.
 * @param pressedScale Target scale while the component is pressed. Use `<1f` to shrink.
 * @param enabled If `false`, returns the receiver unchanged.
 */
public fun Modifier.glassPressAnimation(
    interactionSource: InteractionSource,
    pressedScale: Float = 0.96f,
    enabled: Boolean = true,
): Modifier = if (!enabled) this else composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "glass-press-scale",
    )
    scale(scale)
}
