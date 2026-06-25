package com.fuu.liquidglass.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState

/**
 * The host container that wires up a shared Liquid Glass scene.
 *
 * `LiquidRoot` lays out two stacked layers and connects them through a single
 * [io.github.fletchmckee.liquid.LiquidState]:
 *
 *  1. **Backdrop layer** — composed from [background]. Marked with `Modifier.liquefiable(state)`
 *     so its drawing is captured as the refraction source.
 *  2. **Foreground layer** — composed from [content], drawn on top. Any `Glass*` component here
 *     refracts the backdrop through the same `state`.
 *
 * Keeping the two layers separate is critical: if glass surfaces are drawn *into* the
 * `liquefiable` surface, they refract themselves and you see flat tinted rectangles instead
 * of glass.
 *
 * `LiquidRoot` also publishes the state through [LocalLiquidState] so deeper components
 * (`GlassCard`, `GlassButton`, …) resolve the same scene without manual plumbing.
 *
 * ```
 * setContent {
 *     LiquidRoot(
 *         background = {
 *             Box(
 *                 Modifier
 *                     .fillMaxSize()
 *                     .background(Brush.verticalGradient(listOf(blue, pink)))
 *             )
 *         }
 *     ) {
 *         GlassTheme(style = GlassStyles.Liquid) {
 *             HomeScreen()
 *         }
 *     }
 * }
 * ```
 *
 * @param modifier Modifier applied to the outer container. Defaults to `fillMaxSize()`.
 * @param background The refraction source — wallpaper, image, gradient, animated scene, etc.
 *  Drawn first and marked `liquefiable`. Defaults to empty; if no backdrop is supplied the glass
 *  surfaces will look mostly flat because there is nothing to refract.
 * @param content The foreground layer, drawn over the backdrop. Place every `Glass*` component
 *  here.
 */
@Composable
public fun LiquidRoot(
    modifier: Modifier = Modifier.fillMaxSize(),
    background: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val liquidState = rememberLiquidState()
    CompositionLocalProvider(LocalLiquidState provides liquidState) {
        Box(modifier = modifier) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .liquefiable(liquidState),
                content = background,
            )
            content()
        }
    }
}
