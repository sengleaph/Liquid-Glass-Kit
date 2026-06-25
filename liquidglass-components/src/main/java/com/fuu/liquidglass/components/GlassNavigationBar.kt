package com.fuu.liquidglass.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fuu.liquidglass.animation.glassPressAnimation
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.internal.glassEffect
import com.fuu.liquidglass.theme.LocalGlassStyle

/**
 * A bottom navigation rail rendered on Liquid Glass.
 *
 * Drop a series of [GlassNavigationBarItem]s inside as the row content. Each item handles its own
 * selection state and click animation.
 *
 * ```
 * GlassNavigationBar {
 *     GlassNavigationBarItem(
 *         selected = current == "home",
 *         onClick = { current = "home" },
 *         icon = { Icon(Icons.Default.Home, contentDescription = null) },
 *         label = "Home",
 *     )
 *     // ...
 * }
 * ```
 *
 * @param modifier Applied to the bar container.
 * @param style Overrides `LocalGlassStyle` for this bar only.
 * @param height Height of the bar.
 * @param contentPadding Inner padding around items.
 * @param content Items, laid out evenly across the bar width.
 */
@Composable
public fun GlassNavigationBar(
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    height: Dp = 80.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
    content: @Composable RowScope.() -> Unit,
) {
    val barStyle = style.copy(
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .glassEffect(barStyle)
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        content = content,
    )
}

/**
 * A single tab inside a [GlassNavigationBar]. Stacks an icon over an optional label and applies a
 * gentle Liquid Glass press animation.
 *
 * @param selected Whether this item is currently the active destination.
 * @param onClick Click handler.
 * @param icon Icon slot. Receives `LocalContentColor` according to [selected].
 * @param modifier Applied to the item container.
 * @param enabled When `false`, the item does not respond to input and is rendered at half
 *  opacity.
 * @param label Optional text label drawn below the icon.
 * @param selectedColor Content color when this item is selected.
 * @param unselectedColor Content color when this item is not selected.
 */
@Composable
public fun RowScope.GlassNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String? = null,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val contentColor = when {
        !enabled -> unselectedColor.copy(alpha = 0.38f)
        selected -> selectedColor
        else -> unselectedColor
    }

    Box(
        modifier = modifier
            .weight(1f)
            .glassPressAnimation(interactionSource = interactionSource, enabled = enabled)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = false),
                enabled = enabled,
                onClick = onClick,
            )
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                icon()
                if (label != null) {
                    ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                        Text(text = label)
                    }
                }
            }
        }
    }
}
