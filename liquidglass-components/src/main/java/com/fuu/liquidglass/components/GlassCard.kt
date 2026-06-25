package com.fuu.liquidglass.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fuu.liquidglass.animation.glassPressAnimation
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.internal.glassEffect
import com.fuu.liquidglass.theme.LocalGlassStyle

/**
 * A Liquid Glass card. Stacks children in a `Column` on top of a refractive glass surface.
 *
 * Provide [onClick] to make the card tappable; pressing it runs a subtle Liquid Glass scale
 * animation. Set [animatePress] to `false` to disable the animation while keeping the card
 * clickable.
 *
 * ```
 * GlassCard(modifier = Modifier.fillMaxWidth()) {
 *     Text("Account Balance", style = MaterialTheme.typography.labelMedium)
 *     Text("$12,480.55", style = MaterialTheme.typography.displaySmall)
 * }
 * ```
 *
 * @param modifier Applied to the outer container — use for sizing, padding, weight.
 * @param style Overrides `LocalGlassStyle` for this card only.
 * @param onClick If non-null, the card becomes clickable.
 * @param contentPadding Inner padding applied around [content].
 * @param animatePress Whether tap gestures animate a subtle press shrink.
 * @param content Card content, laid out top-to-bottom in a `Column`.
 */
@Composable
public fun GlassCard(
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    animatePress: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val pressModifier = if (onClick != null && animatePress) {
        Modifier.glassPressAnimation(interactionSource = interactionSource)
    } else {
        Modifier
    }

    val clickableModifier = if (onClick != null) {
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = ripple(bounded = true),
            onClick = onClick,
        )
    } else {
        Modifier
    }

    Column(
        modifier = modifier
            .then(pressModifier)
            .glassEffect(style)
            .then(clickableModifier)
            .padding(contentPadding),
        content = content,
    )
}
