# LiquidGlassKit

A production-ready Jetpack Compose design-system library that delivers Apple-inspired **Liquid
Glass** UI components for Android. Built on top of
[`io.github.fletchmckee:liquid`](https://central.sonatype.com/artifact/io.github.fletchmckee/liquid),
LiquidGlassKit hides the low-level `LiquidScope` DSL behind a small, theme-aware component set so
your screens stay declarative and clean.

```
LiquidRoot(
    background = {
        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(blue, pink)))
        )
    }
) {
    GlassTheme(style = GlassStyles.Liquid) {
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text("Account Balance")
            Text("$12,480.55", style = MaterialTheme.typography.displaySmall)
        }
        GlassButton(text = "Continue", onClick = { /* ... */ })
    }
}
```

---

## Modules

| Module | Purpose |
| --- | --- |
| **`:liquidglass-core`** | Pure data: `GlassStyle`, `GlassStyles` presets. Zero composables. |
| **`:liquidglass-theme`** | `GlassTheme`, `LiquidRoot`, `LocalGlassStyle`, `LocalLiquidState`. |
| **`:liquidglass-components`** | All `Glass*` components and the optional animation modifiers. |
| **`:sample`** | Showcase app — `MainActivity.kt` exercises every component. |

Architecture follows Clean Architecture: each module depends only on lower layers, never on
upper ones. `:liquidglass-core` is the bottom of the stack; `:liquidglass-components` brings
everything together.

```
:sample ─▶ :liquidglass-components ─▶ :liquidglass-theme ─▶ :liquidglass-core
                       │                       │
                       └───────────────────────┴──▶ io.github.fletchmckee:liquid
```

---

## Install

`gradle/libs.versions.toml`:

```toml
[versions]
liquid = "0.3.0"

[libraries]
liquid = { group = "io.github.fletchmckee", name = "liquid", version.ref = "liquid" }
```

In your app module:

```kotlin
dependencies {
    implementation(project(":liquidglass-core"))
    implementation(project(":liquidglass-theme"))
    implementation(project(":liquidglass-components"))
}
```

> **Note:** This repo vendors LiquidGlassKit as source. To publish to Maven, wire each library
> module to your favorite publishing plugin (`com.vanniktech.maven.publish` is recommended) and
> consumers can drop `implementation("io.github.fletchmckee:liquid:0.3.0")` straight into their
> own apps.

### Requirements

- Android `minSdk = 24` (API 24 / Nougat) for the library to compile and not crash.
- **Android 13+ (API 33+) on the device for the full Liquid Glass visuals.**
- Kotlin `2.0.21+`
- Jetpack Compose BOM `2024.09.00+` (Material 3)
- Compose Compiler plugin (`org.jetbrains.kotlin.plugin.compose`)

### Android-version fallback (from the underlying liquid library)

The upstream effect uses an Android `RuntimeShader`, which only exists on API 33+:

| Android level | What renders |
| --- | --- |
| **13+ (API 33+)** | Full Liquid Glass — refraction, curve, dispersion, frost, edge, tint, contrast, saturation. |
| **12 (API 32)** | Frost + tint + edge + contrast + saturation. Refraction, curve, dispersion are no-ops. |
| **11 and lower (≤ API 30)** | Tint + edge + contrast + saturation only. Frost is also a no-op. |

If you test on a Pixel emulator running **API 30 / Android 11**, you will see flat tinted
rectangles with no blur or refraction — *not because the wrapper is broken*, but because the
shader pipeline doesn't exist on that OS. **Run the sample on an API 33+ emulator** (e.g.
Pixel 7 / Android 13 image) and the glass effect will show up immediately.

---

## Core concepts

### `GlassStyle`

Every glass surface is described by a single immutable [`GlassStyle`](liquidglass-core/src/main/java/com/fuu/liquidglass/core/GlassStyle.kt)
data class. The nine properties map 1:1 to the `LiquidScope` DSL on `Modifier.liquid {}`:

| Property | Type | What it does |
| --- | --- | --- |
| `frost` | `Dp` | Backdrop blur radius. |
| `tint` | `Color` | Color washed over the blurred backdrop. |
| `edge` | `Float` | Rim highlight intensity (`0f..1f`). |
| `contrast` | `Float` | Backdrop contrast multiplier. |
| `saturation` | `Float` | Backdrop saturation multiplier. |
| `refraction` | `Float` | Edge refraction strength (`0f..1f`). |
| `dispersion` | `Float` | Chromatic dispersion (rainbow fringe). |
| `curve` | `Float` | Glass curvature. |
| `shape` | `Shape` | Outline shape, used both for clipping and rim path. |

### `GlassStyles`

Curated presets in [`GlassStyles`](liquidglass-core/src/main/java/com/fuu/liquidglass/core/GlassStyles.kt):

- **`Clear`** — near-invisible glass, light blur, soft edge.
- **`Frosted`** — the Control Center default. *(also exposed as `GlassStyles.Default`)*
- **`Crystal`** — sharper, brighter rim, stronger refraction.
- **`Liquid`** — the flagship preset; cool-blue tint, generous rounding.
- **`Neon`** — magenta-tinted, hyper-saturated, exaggerated chromatic dispersion.

### `LiquidRoot`

Wrap the topmost screen in [`LiquidRoot`](liquidglass-theme/src/main/java/com/fuu/liquidglass/theme/LiquidRoot.kt).
It lays out two stacked layers and connects them through a single shared `LiquidState`:

- **`background`** — the refraction source (wallpaper, gradient, image). Drawn into the
  `liquefiable` surface.
- **`content`** — the foreground; put every `Glass*` component here.

Keeping the two slots separate is critical: if glass surfaces are drawn into the same layer
as the backdrop, they refract themselves and the effect collapses into flat tinted rectangles.

```kotlin
setContent {
    LiquidRoot(
        background = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(blue, pink)))
            )
        }
    ) {
        GlassTheme(style = GlassStyles.Liquid) {
            HomeScreen()
        }
    }
}
```

`LiquidRoot` publishes the state through `LocalLiquidState`; you never have to thread it
manually. Components fall back to a private state when no `LiquidRoot` is in scope so
`@Preview` works out of the box.

### `GlassTheme`

[`GlassTheme`](liquidglass-theme/src/main/java/com/fuu/liquidglass/theme/GlassTheme.kt) provides
the active style to descendants and wraps Material 3. Nest `GlassTheme` calls to scope a
different look to a subtree:

```kotlin
GlassTheme(style = GlassStyles.Liquid) {
    GlassCard { /* ... */ }

    // Override just this section:
    GlassTheme(style = GlassStyles.Neon) {
        GlassButton(text = "Buy", onClick = {})
    }
}
```

---

## Components

All `Glass*` components live in `com.fuu.liquidglass.components`. Each reads `LocalGlassStyle`
by default and accepts a `style` parameter for ad-hoc overrides.

| Component | What it is |
| --- | --- |
| `GlassSurface` | Base primitive — a `Box` with a glass effect. |
| `GlassCard` | `Column`-shaped glass tile, optionally clickable with a press animation. |
| `GlassButton` | Pill-shaped button with text + optional leading/trailing icon slots. |
| `GlassIconButton` | Circular icon button. |
| `GlassFloatingActionButton` / `GlassFAB` | Rounded FAB with an optional float animation. |
| `GlassTopBar` | App bar — leading nav icon, title, trailing actions. |
| `GlassBottomBar` | Bottom container for primary actions. |
| `GlassNavigationBar` + `GlassNavigationBarItem` | Bottom navigation rail with selection state. |
| `GlassDialog` | Modal dialog with a Liquid Glass surface. |

---

## Animations

Optional modifier-style animations under `com.fuu.liquidglass.animation`. Each accepts an
`enabled` flag so you can keep call sites uniform.

| Modifier | Effect |
| --- | --- |
| `Modifier.glassPressAnimation(interactionSource)` | Springy scale-down while pressed. |
| `Modifier.glassHoverAnimation(interactionSource)` | Lift on hover (tablets, foldables). |
| `Modifier.glassFloatAnimation()` | Continuous vertical bob — used by `GlassFAB`. |
| `Modifier.glassShimmerAnimation()` | Sweeping highlight band — great for loading states. |

```kotlin
val interactionSource = remember { MutableInteractionSource() }
GlassSurface(
    modifier = Modifier
        .glassPressAnimation(interactionSource)
        .clickable(interactionSource, indication = ripple()) { onClick() }
) { /* ... */ }
```

---

## Theming

`GlassTheme` wraps `MaterialTheme`, so all Material 3 typography, color and shape APIs continue
to work. Dark mode follows the system by default — pass `useDarkTheme = false` to force light.

```kotlin
GlassTheme(
    style = GlassStyles.Liquid,
    colorScheme = darkColorScheme(primary = Color(0xFF8FB8FF)),
    typography = MyAppTypography,
) {
    /* ... */
}
```

---

## Package structure

```
com.fuu.liquidglass
├── core/         # GlassStyle, GlassStyles
├── theme/        # GlassTheme, LiquidRoot, LocalGlassStyle, LocalLiquidState
├── components/   # GlassCard, GlassButton, GlassIconButton, GlassFAB,
│                 # GlassTopBar, GlassBottomBar, GlassNavigationBar, GlassDialog,
│                 # GlassSurface
├── animation/    # GlassPressAnimation, GlassHoverAnimation,
│                 # GlassFloatAnimation, GlassShimmerAnimation
└── internal/     # Bridge between GlassStyle and the upstream Liquid DSL
```

---

## Design goals

- **Apple Liquid Glass look** — generous corner radii, strong rim refraction, soft frost, vivid
  tint.
- **Material 3 compatible** — every component honors the active `ColorScheme`, `Typography` and
  `Shapes`.
- **Light + Dark support** — `GlassTheme` follows the system theme by default.
- **Zero `LiquidScope` exposure** — consumers never see the underlying DSL.
- **Strict public API** — all library modules build with `-Xexplicit-api=strict` so the surface
  area is well-defined and stable.

---

## Sample app

Run the `:sample` module to see every component live:

```
./gradlew :sample:installDebug
```

The sample exercises `LiquidRoot`, three nested `GlassTheme` scopes, a vivid gradient backdrop,
and every `Glass*` component including the dialog.

---

## License

```
Copyright 2026 LiquidGlassKit contributors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```
