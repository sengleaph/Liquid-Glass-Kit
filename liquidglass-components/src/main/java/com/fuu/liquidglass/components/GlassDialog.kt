package com.fuu.liquidglass.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.internal.glassEffect
import com.fuu.liquidglass.theme.LocalGlassStyle

/**
 * A modal dialog whose surface is rendered as Liquid Glass.
 *
 * Wraps [androidx.compose.ui.window.Dialog] so it inherits all standard dialog behavior
 * (back-press handling, scrim, focus traversal). The dialog content is laid out in a `Column`
 * with the supplied padding.
 *
 * ```
 * GlassDialog(onDismissRequest = { open = false }) {
 *     Text("Are you sure?", style = MaterialTheme.typography.titleLarge)
 *     Spacer(Modifier.height(16.dp))
 *     GlassButton(text = "Confirm", onClick = ::confirm)
 * }
 * ```
 *
 * @param onDismissRequest Called when the user dismisses the dialog (tap outside / back press).
 * @param modifier Applied to the dialog surface (NOT the underlying scrim).
 * @param style Overrides `LocalGlassStyle` for the dialog surface only.
 * @param properties Standard [DialogProperties].
 * @param contentPadding Inner padding around [content].
 * @param content Dialog content, laid out top-to-bottom in a `Column`.
 */
@Composable
public fun GlassDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    properties: DialogProperties = DialogProperties(),
    contentPadding: PaddingValues = PaddingValues(24.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest, properties = properties) {
        Column(
            modifier = modifier
                .sizeIn(minWidth = 280.dp, maxWidth = 560.dp)
                .glassEffect(style)
                .padding(contentPadding),
            content = content,
        )
    }
}
