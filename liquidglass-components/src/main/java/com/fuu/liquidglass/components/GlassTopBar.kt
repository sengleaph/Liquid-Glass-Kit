package com.fuu.liquidglass.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.internal.glassEffect
import com.fuu.liquidglass.theme.LocalGlassStyle

/**
 * A horizontal Liquid Glass top app bar.
 *
 * Layout matches Material's `TopAppBar` — a navigation slot on the leading side, a title in the
 * middle, and trailing actions — but built on a Liquid Glass surface. The bar fills its parent
 * width and is 64.dp tall by default.
 *
 * ```
 * GlassTopBar(
 *     title = { Text("Wallet") },
 *     navigationIcon = {
 *         GlassIconButton(onClick = ::back) { Icon(Icons.Default.ArrowBack, null) }
 *     },
 *     actions = {
 *         GlassIconButton(onClick = ::menu) { Icon(Icons.Default.MoreVert, null) }
 *     },
 * )
 * ```
 *
 * @param title Title slot. Receives the Material `titleLarge` text style by default.
 * @param modifier Applied to the bar container.
 * @param style Overrides `LocalGlassStyle` for this bar only.
 * @param navigationIcon Leading slot. Typically a `GlassIconButton` with a back arrow.
 * @param actions Trailing slot, laid out as a `Row`. Typically `GlassIconButton`s.
 * @param contentPadding Inner padding around all slots.
 */
@Composable
public fun GlassTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .glassEffect(style)
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface) {
            navigationIcon()
            ProvideTextStyle(value = MaterialTheme.typography.titleLarge) {
                Row(modifier = Modifier.weight(1f)) { title() }
            }
            actions()
        }
    }
}
