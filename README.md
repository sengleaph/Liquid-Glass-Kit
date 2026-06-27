# LiquidGlassKit

[![](https://jitpack.io/v/sengleaph/liquidglass.svg)](https://jitpack.io/#sengleaph/liquidglass)

**Apple-inspired Liquid Glass UI components for Jetpack Compose.**

```kotlin
GlassTheme(style = GlassStyles.Liquid) {
    LiquidRoot(
        background = { ColorfulBackdrop() }
    ) {
        GlassCard {
            Text("Hello, Liquid Glass", style = MaterialTheme.typography.titleLarge)
        }
    }
}
```

---

## Install

### Step 1 — Add JitPack to `settings.gradle.kts`

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2 — Add ONE dependency to `app/build.gradle.kts`

```kotlin
dependencies {
    implementation("com.github.sengleaph.liquidglass:liquidglass:0.3.0")
}
```

That single line pulls in `:liquidglass-core`, `:liquidglass-theme`, and `:liquidglass-components`
transitively — every `Glass*` component, every preset, every animation modifier.

### Step 3 — Sync Gradle

First sync takes 1–3 minutes (JitPack is building the library); every sync after is instant.

---

## Modules

| Module | Purpose |
| --- | --- |
| **`:liquidglass`** | **Umbrella — re-exports the three modules below. Use this 99% of the time.** |
| `:liquidglass-core` | Pure data: `GlassStyle`, `GlassStyles` presets |
| `:liquidglass-theme` | `GlassTheme`, `LiquidRoot`, `LocalGlassStyle`, `LocalLiquidState` |
| `:liquidglass-components` | All `Glass*` components and the optional animation modifiers |
| `:sample` | Showcase app — exercises every component over a vivid backdrop |

Dependency direction (always downward, never upward):

```
                         ┌──▶ :liquidglass-core
:sample ─▶ :liquidglass ─┼──▶ :liquidglass-theme ──▶ :liquidglass-core
                         └──▶ :liquidglass-components ─▶ :liquidglass-theme + :liquidglass-core
                                          │
                                          └──▶ io.github.fletchmckee.liquid
```

### Granular install (optional)

If you only need styles and theming, skip the umbrella and depend on individual modules:

```kotlin
dependencies {
    implementation("com.github.sengleaph.liquidglass:liquidglass-core:0.3.0")
    implementation("com.github.sengleaph.liquidglass:liquidglass-theme:0.3.0")
    implementation("com.github.sengleaph.liquidglass:liquidglass-components:0.3.0")
}
```

---

## Tutorial — your first Liquid Glass screen

### 1. Wrap your screen in `LiquidRoot`

`LiquidRoot` is the host. It sets up a single shared refraction "scene" — a backdrop layer that
gets captured, and a foreground layer where your glass surfaces live.

```kotlin
setContent {
    LiquidRoot(
        background = {
            // anything drawn here is what glass surfaces refract
        }
    ) {
        // every Glass* component goes here
    }
}
```

> **Important:** the two slots are NOT interchangeable. If you put glass surfaces inside the
> `background` slot they'll refract themselves and look flat. Keep them in `content`.

### 2. Give it a backdrop worth refracting

Glass needs something colorful behind it. A gradient is the easiest:

```kotlin
LiquidRoot(
    background = {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1B3CFF), Color(0xFFFF3A8B))
                    )
                )
        )
    }
) { /* content next */ }
```

### 3. Pick a style with `GlassTheme`

```kotlin
GlassTheme(style = GlassStyles.Liquid) {
    LiquidRoot(background = { /* ... */ }) {
        /* glass components see GlassStyles.Liquid as the default */
    }
}
```

Five presets are bundled:

| Preset | Look |
| --- | --- |
| `GlassStyles.Clear` | Near-invisible glass, light blur |
| `GlassStyles.Frosted` | Apple Control Center default |
| `GlassStyles.Crystal` | Sharper, brighter rim |
| `GlassStyles.Liquid` | Cool-blue tint, generous rounding (flagship preset) |
| `GlassStyles.Neon` | Magenta-tinted, hyper-saturated, chromatic dispersion |

### 4. Drop in some `Glass*` components

```kotlin
GlassTheme(style = GlassStyles.Liquid) {
    LiquidRoot(background = { /* gradient */ }) {
        Column(
            Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            GlassCard(Modifier.fillMaxWidth()) {
                Text("Account Balance", style = MaterialTheme.typography.labelMedium)
                Text("$12,480.55", style = MaterialTheme.typography.displaySmall)
            }
            GlassButton(text = "Continue", onClick = { /* ... */ })
        }
    }
}
```

---

## Components

All components live in `com.fuu.liquidglass.components`. Each reads `LocalGlassStyle` by default
and accepts a `style` parameter for one-off overrides.

| Component | What it is |
| --- | --- |
| `GlassSurface` | Base primitive — a `Box` with a glass effect |
| `GlassCard` | `Column`-shaped tile, optionally clickable with a press animation |
| `GlassButton` | Pill-shaped button with text + optional leading/trailing icon |
| `GlassIconButton` | Circular icon-only button |
| `GlassFloatingActionButton` / `GlassFAB` | Rounded FAB with optional float animation |
| `GlassTopBar` | App bar — leading nav, title, trailing actions |
| `GlassBottomBar` | Bottom container for primary actions |
| `GlassNavigationBar` + `GlassNavigationBarItem` | Bottom navigation rail with selection state |
| `GlassDialog` | Modal dialog rendered on a glass surface |

### Optional animations

Modifier extensions you can chain on any glass component:

| Modifier | Effect |
| --- | --- |
| `Modifier.glassPressAnimation(interactionSource)` | Springy scale-down while pressed |
| `Modifier.glassHoverAnimation(interactionSource)` | Lift on hover (tablets, foldables) |
| `Modifier.glassFloatAnimation()` | Continuous vertical bob — used by `GlassFAB` |
| `Modifier.glassShimmerAnimation()` | Sweeping highlight band — great for loading states |

---

## Customizing the look

### Tweak a preset with `.copy()`

```kotlin
val MyGold = GlassStyles.Frosted.copy(
    tint = Color(0xFFFFD700).copy(alpha = 0.18f),
    edge = 0.8f,
)

GlassCard(style = MyGold) { /* ... */ }
```

### Build a new style from scratch

```kotlin
val Aqua = GlassStyle(
    frost = 22.dp,
    tint = Color(0xFF00C2FF).copy(alpha = 0.18f),
    edge = 0.85f,
    contrast = 1.10f,
    saturation = 1.25f,
    refraction = 0.55f,
    dispersion = 0.12f,
    curve = 1.10f,
    shape = RoundedCornerShape(20.dp),
)
```

### Property cheat sheet

| Want… | Tweak | Direction |
| --- | --- | --- |
| Heavier blur | `frost` | up: `8.dp → 32.dp → 64.dp` |
| Brighter rim | `edge` | up: `0f → 1f` |
| Stronger lens | `refraction` + `curve` | both up: `0.2f → 0.9f` |
| Rainbow fringe | `dispersion` | up: `0f → 0.4f` |
| More vivid backdrop | `saturation` | up: `1.0f → 1.4f` |
| Color wash | `tint` | change hue, **keep alpha ≤ 0.25** |
| Rounder corners | `shape` | `RoundedCornerShape(24.dp → 36.dp)` |
| Circle | `shape` | `CircleShape` |

### Nested overrides

```kotlin
GlassTheme(style = GlassStyles.Liquid) {
    GlassCard { Text("Liquid") }

    // Override just this subtree:
    GlassTheme(style = GlassStyles.Neon) {
        GlassButton(text = "Neon", onClick = {})
    }
}
```

---

## Requirements

| | Minimum | Recommended |
| --- | --- | --- |
| Android `minSdk` | 24 (library compiles and won't crash) | 33+ (full Liquid Glass effect) |
| Kotlin | 2.0+ | 2.0.21+ |
| Compose BOM | 2024.09.00+ | 2024.09.00+ |
| Compose Compiler plugin | required since Kotlin 2.0 | — |

### Android-version fallback

The underlying liquid shader needs Android 13+ (API 33). Below that, the library gracefully
degrades — your code keeps working, the visuals get simpler:

| Device API level | What renders |
| --- | --- |
| **API 33+ (Android 13)** | Full Liquid Glass — refraction, frost, edge, dispersion, curve |
| **API 32 (Android 12)** | Frost + tint + edge only (no refraction / curve / dispersion) |
| **API ≤ 30 (Android 11)** | Tint + edge only (frost also disabled) |

If a card looks like a flat tinted rectangle, you're almost certainly on a device below API 33.
Test on a Pixel 7 / Android 13+ emulator.

---

## Sample app

The `:sample` module ships a full demo with every component over a gradient backdrop, a
real-time style tuner, and a nested `GlassTheme(Neon)` override:

```powershell
.\gradlew :sample:installDebug
```

---

## License

```
Copyright 2026 sengleaph

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```

---

## Credits

LiquidGlassKit is a thin wrapper around the excellent
[FletchMcKee/liquid](https://github.com/FletchMcKee/liquid) library by Colin McKee — that's the
project doing the real GPU shader work. All credit for the underlying effect goes there.
