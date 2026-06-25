package com.fuu.liquidglass.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fuu.liquidglass.animation.glassPressAnimation
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.internal.glassEffect
import com.fuu.liquidglass.theme.LocalGlassStyle

/**
 * A pill-shaped Liquid Glass button.
 *
 * Wraps a single horizontal row of content over a glass surface. Two forms are provided: a
 * text-first overload for the most common case, and a slot-based overload for full control.
 *
 * ```
 * GlassButton(text = "Continue", onClick = { /* ... */ })
 * ```
 */
@Composable
public fun GlassButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    contentPadding: PaddingValues = ButtonDefaultPadding,
) {
    GlassButton(
        onClick = onClick,
        modifier = modifier,
        style = style,
        enabled = enabled,
        contentPadding = contentPadding,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(Modifier.width(8.dp))
        }
        Text(text = text)
        if (trailingIcon != null) {
            Spacer(Modifier.width(8.dp))
            trailingIcon()
        }
    }
}

/**
 * Slot-based variant of [GlassButton]. Use when the label is more complex than a single string.
 *
 * @param onClick Click handler. Ignored when [enabled] is `false`.
 * @param modifier Applied to the outer container.
 * @param style Overrides `LocalGlassStyle` for this button only.
 * @param enabled When `false`, the button does not respond to input and is rendered at half
 *  opacity.
 * @param contentPadding Inner padding around [content].
 * @param horizontalArrangement Horizontal arrangement of children inside the row.
 * @param content Row content. Typically text and/or icons.
 */
@Composable
public fun GlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaultPadding,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val contentColor = if (enabled) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }

    Row(
        modifier = modifier
            .glassPressAnimation(interactionSource = interactionSource, enabled = enabled)
            .glassEffect(style)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true),
                enabled = enabled,
                onClick = onClick,
            )
            .padding(contentPadding),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
                content()
            }
        }
    }
}

private val ButtonDefaultPadding: PaddingValues =
    PaddingValues(horizontal = 24.dp, vertical = 14.dp)
