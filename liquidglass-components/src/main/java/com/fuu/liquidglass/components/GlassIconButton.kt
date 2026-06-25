package com.fuu.liquidglass.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fuu.liquidglass.animation.glassPressAnimation
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.internal.glassEffect
import com.fuu.liquidglass.theme.LocalGlassStyle

/**
 * A circular Liquid Glass icon button.
 *
 * Drops the supplied [content] (typically an `Icon`) inside a circular glass surface with an
 * unbounded ripple. The shape always overrides the active style's shape so the button stays
 * round, regardless of the inherited [GlassStyle].
 *
 * ```
 * GlassIconButton(onClick = { /* ... */ }) {
 *     Icon(Icons.Default.Settings, contentDescription = "Settings")
 * }
 * ```
 *
 * @param onClick Click handler. Ignored when [enabled] is `false`.
 * @param modifier Applied to the outer container.
 * @param size The touch and visual diameter of the button.
 * @param enabled When `false`, the button does not respond to input and is rendered at half
 *  opacity.
 * @param style Overrides `LocalGlassStyle` for this button only.
 * @param content Drawn at the center of the button. Typically an `Icon`.
 */
@Composable
public fun GlassIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    enabled: Boolean = true,
    style: GlassStyle = LocalGlassStyle.current,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val circleStyle = style.copy(shape = CircleShape)
    val contentColor = if (enabled) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }

    Box(
        modifier = modifier
            .size(size)
            .glassPressAnimation(interactionSource = interactionSource, enabled = enabled)
            .glassEffect(circleStyle)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = false, radius = size / 2),
                enabled = enabled,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}
