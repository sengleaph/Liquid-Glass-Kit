package com.fuu.liquidglass.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.internal.glassEffect
import com.fuu.liquidglass.theme.LocalGlassStyle

/**
 * The lowest-level Liquid Glass primitive — a plain `Box` that draws a refractive surface using
 * the active [GlassStyle].
 *
 * `GlassSurface` is the building block under every other `Glass*` component. Use it directly when
 * the higher-level components don't fit (custom layouts, ad-hoc panels, etc.).
 *
 * ```
 * GlassSurface(modifier = Modifier.size(200.dp)) {
 *     Text("Custom panel", Modifier.align(Alignment.Center))
 * }
 * ```
 *
 * @param modifier Applied after the glass effect — use it for sizing, padding and layout.
 * @param style Overrides the style provided by `GlassTheme` for just this surface.
 * @param contentAlignment Default alignment for unaligned children inside the underlying `Box`.
 * @param propagateMinConstraints Forwarded to the underlying `Box`.
 * @param content The composables drawn on top of the glass.
 */
@Composable
public fun GlassSurface(
    modifier: Modifier = Modifier,
    style: GlassStyle = LocalGlassStyle.current,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .glassEffect(style)
            .then(modifier),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
        content = content,
    )
}
