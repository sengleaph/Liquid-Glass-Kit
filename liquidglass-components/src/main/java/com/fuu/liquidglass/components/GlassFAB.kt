package com.fuu.liquidglass.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.fuu.liquidglass.animation.glassFloatAnimation
import com.fuu.liquidglass.animation.glassPressAnimation
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.internal.glassEffect
import com.fuu.liquidglass.theme.LocalGlassStyle

/**
 * A Liquid Glass floating action button.
 *
 * Renders a rounded glass tile that hovers above the surrounding content. Pass [floatAnimation]
 * `true` (the default) to enable a gentle bobbing motion that emphasizes the floating illusion.
 *
 * ```
 * GlassFloatingActionButton(onClick = { /* ... */ }) {
 *     Icon(Icons.Default.Add, contentDescription = "Add")
 * }
 * ```
 *
 * @param onClick Click handler.
 * @param modifier Applied to the outer container.
 * @param style Overrides `LocalGlassStyle` for this FAB only. The shape is replaced with a
 *  rounded square to keep Apple-style proportions.
 * @param size Visual size of the FAB.
 * @param floatAnimation If `true`, the FAB bobs vertically.
 * @param content Drawn centered inside the FAB. Typically an `Icon`.
 */
@Composable
public fun GlassFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    size: Dp = 64.dp,
    floatAnimation: Boolean = true,
    content: @Composable () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val fabStyle = style.copy(shape = RoundedCornerShape(percent = 30))

    Box(
        modifier = modifier
            .glassFloatAnimation(enabled = floatAnimation)
            .glassPressAnimation(interactionSource = interactionSource)
            .defaultMinSize(minWidth = size, minHeight = size)
            .sizeIn(minWidth = size, minHeight = size)
            .glassEffect(fabStyle)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
                onClick = onClick,
            )
            .padding(12.dp),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            content()
        }
    }
}

/** Shorter alias for [GlassFloatingActionButton]. */
@Composable
public fun GlassFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    size: Dp = 64.dp,
    floatAnimation: Boolean = true,
    content: @Composable () -> Unit,
): Unit = GlassFloatingActionButton(
    onClick = onClick,
    modifier = modifier,
    style = style,
    size = size,
    floatAnimation = floatAnimation,
    content = content,
)
