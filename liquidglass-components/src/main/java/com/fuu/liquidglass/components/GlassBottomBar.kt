package com.fuu.liquidglass.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.internal.glassEffect
import com.fuu.liquidglass.theme.LocalGlassStyle

/**
 * A Liquid Glass bottom container. Use it as a footer that holds primary actions or summary text
 * over a refractive surface.
 *
 * Unlike [GlassNavigationBar], `GlassBottomBar` does not impose item layout — children are
 * arranged in a `Row` with the supplied arrangement.
 *
 * @param modifier Applied to the bar container.
 * @param style Overrides `LocalGlassStyle` for this bar only.
 * @param height Height of the bar.
 * @param contentPadding Inner padding around children.
 * @param horizontalArrangement Row arrangement of children.
 * @param content Bar content, laid out left-to-right.
 */
@Composable
public fun GlassBottomBar(
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    height: Dp = 72.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    content: @Composable RowScope.() -> Unit,
) {
    val bottomStyle = style.copy(
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .glassEffect(bottomStyle)
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
        content = content,
    )
}
