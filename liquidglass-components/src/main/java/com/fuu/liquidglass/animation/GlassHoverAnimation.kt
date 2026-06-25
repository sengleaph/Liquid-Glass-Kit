package com.fuu.liquidglass.animation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale

/**
 * Subtly lifts the component while the pointer hovers over it.
 *
 * Hover is meaningful on tablets, foldables and Chromebooks; on phones the animation is a no-op
 * because no hover interactions are emitted. Pair with an [InteractionSource] from a
 * `Modifier.hoverable(...)` or `Modifier.clickable(...)` call.
 *
 * @param interactionSource The source whose `Hover` interactions drive the animation.
 * @param hoveredScale Target scale while the pointer hovers. Use `>1f` to lift.
 * @param enabled If `false`, returns the receiver unchanged.
 */
public fun Modifier.glassHoverAnimation(
    interactionSource: InteractionSource,
    hoveredScale: Float = 1.025f,
    enabled: Boolean = true,
): Modifier = if (!enabled) this else composed {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val scale by animateFloatAsState(
        targetValue = if (isHovered) hoveredScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "glass-hover-scale",
    )
    scale(scale)
}
