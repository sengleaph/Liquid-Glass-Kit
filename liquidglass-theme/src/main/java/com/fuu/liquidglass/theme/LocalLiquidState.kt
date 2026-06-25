package com.fuu.liquidglass.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.rememberLiquidState

/**
 * The active [LiquidState] used by every component below `LiquidRoot`.
 *
 * Components such as `GlassCard` resolve this implicitly when they call `Modifier.liquid()`. The
 * value is `null` when no `LiquidRoot` is in scope; in that case [currentLiquidStateOrFallback]
 * remembers a private state so components remain renderable in isolation (e.g. inside a Compose
 * `@Preview`).
 */
public val LocalLiquidState: ProvidableCompositionLocal<LiquidState?> =
    staticCompositionLocalOf { null }

/**
 * Returns the nearest [LiquidState] from the composition, falling back to a freshly remembered
 * one when none is provided. Prefer wrapping screens in `LiquidRoot` so all components share the
 * same liquid scene; this fallback exists to keep previews working.
 */
@Composable
public fun currentLiquidStateOrFallback(): LiquidState =
    LocalLiquidState.current ?: rememberLiquidState()
