package com.fuu.liquidglass.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fuu.liquidglass.components.GlassButton
import com.fuu.liquidglass.components.GlassCard
import com.fuu.liquidglass.components.GlassDialog
import com.fuu.liquidglass.components.GlassFloatingActionButton
import com.fuu.liquidglass.components.GlassIconButton
import com.fuu.liquidglass.components.GlassNavigationBar
import com.fuu.liquidglass.components.GlassNavigationBarItem
import com.fuu.liquidglass.components.GlassTopBar
import com.fuu.liquidglass.core.GlassStyle
import com.fuu.liquidglass.core.GlassStyles
import com.fuu.liquidglass.theme.GlassTheme
import com.fuu.liquidglass.theme.LiquidRoot
import io.github.fletchmckee.liquid.liquefiable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlassTheme(
                style = GlassStyles.Liquid,
                useDarkTheme = isSystemInDarkTheme(),
            ) {
                LiquidRoot(
                    background = { AnimatedBackdrop() },
                ) {
                    LiquidShowcase()
                }
            }
        }
    }
}

private val SunsetGlass = GlassStyle(
    frost = 24.dp,
    tint = Color(0xFFFF7A59).copy(alpha = 0.20f),
    edge = 0.9f,
    contrast = 1.10f,
    saturation = 1.30f,
    refraction = 0.70f,
    dispersion = 0.18f,
    curve = 1.20f,
    shape = RoundedCornerShape(28.dp),
)

private val ObsidianGlass = GlassStyle(
    frost = 30.dp,
    tint = Color(0xFF0B0B14).copy(alpha = 0.32f),
    edge = 0.75f,
    contrast = 1.20f,
    saturation = 0.85f,
    refraction = 0.50f,
    dispersion = 0.05f,
    curve = 1.00f,
    shape = RoundedCornerShape(20.dp),
)
@Composable
private fun AnimatedBackdrop() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.bg_app),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
    // Floating colored blobs to demonstrate refraction across the glass surfaces.
//    Box(
//        modifier = Modifier
//            .padding(start = 40.dp, top = 200.dp)
//            .size(180.dp)
//            .clip(RoundedCornerShape(50))
//            .background(Color(0xFFFFC400)),
//    )
//    Box(
//        modifier = Modifier
//            .padding(start = 220.dp, top = 520.dp)
//            .size(220.dp)
//            .clip(RoundedCornerShape(50))
//            .background(Color(0xFF14E3D7)),
//    )
}
@Composable
private fun StyleTuner() {
    var frost by remember { mutableFloatStateOf(24f) }      // in dp
    var edge by remember { mutableFloatStateOf(0.6f) }
    var refraction by remember { mutableFloatStateOf(0.6f) }
    var curve by remember { mutableFloatStateOf(1.0f) }
    var dispersion by remember { mutableFloatStateOf(0.15f) }
    var tintAlpha by remember { mutableFloatStateOf(0.18f) }

    val style = GlassStyle(
        frost = frost.dp,
        tint = Color(0xFF8FB8FF).copy(alpha = tintAlpha),
        edge = edge,
        contrast = 1.1f,
        saturation = 1.3f,
        refraction = refraction,
        dispersion = dispersion,
        curve = curve,
        shape = RoundedCornerShape(28.dp),
    )

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        GlassCard(style = style, modifier = Modifier.fillMaxWidth().height(140.dp)) {
            Text("Live tuned glass", color = Color.White)
        }

        Slider(value = frost, onValueChange = { frost = it }, valueRange = 0f..64f)
        Text("frost = ${frost.toInt()}.dp")

        Slider(value = edge, onValueChange = { edge = it }, valueRange = 0f..1f)
        Text("edge = ${"%.2f".format(edge)}")

        Slider(value = refraction, onValueChange = { refraction = it }, valueRange = 0f..1f)
        Text("refraction = ${"%.2f".format(refraction)}")

        Slider(value = curve, onValueChange = { curve = it }, valueRange = 0f..2f)
        Text("curve = ${"%.2f".format(curve)}")

        Slider(value = dispersion, onValueChange = { dispersion = it }, valueRange = 0f..0.5f)
        Text("dispersion = ${"%.2f".format(dispersion)}")

        Slider(value = tintAlpha, onValueChange = { tintAlpha = it }, valueRange = 0f..0.5f)
        Text("tint.alpha = ${"%.2f".format(tintAlpha)}")
    }
}
@Composable
private fun LiquidShowcase() {
    var selectedTab by remember { mutableStateOf(0) }
    var dialogOpen by remember { mutableStateOf(false) }
    val scroll = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, bottom = 96.dp)
                .verticalScroll(scroll),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Spacer(Modifier.height(8.dp))

            GlassCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                Text(
                    text = "Account Balance",
                    style = MaterialTheme.typography.labelMedium,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$12,480.55",
                    style = MaterialTheme.typography.displaySmall,
                )
            }

            GlassCard(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                style = GlassStyles.Crystal,
                onClick = { /* tap demo */ },
            ) {
                Text("Crystal preset", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Tap me for the press animation.",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            StyleTuner()

            GlassTheme(style = GlassStyles.Neon) {
                GlassCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    Text("Neon override", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Nested GlassTheme scopes the style to this subtree.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                GlassButton(
                    text = "Continue",
                    onClick = { /* primary action */ },
                    modifier = Modifier.weight(1f),
                )
                GlassButton(
                    text = "Confirm…",
                    onClick = { dialogOpen = true },
                    modifier = Modifier.weight(1f),
                    leadingIcon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                )
            }

            Spacer(Modifier.height(80.dp))
        }

        GlassTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            title = { Text("LiquidGlassKit") },
            navigationIcon = {
                GlassIconButton(onClick = { /* back */ }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                GlassIconButton(onClick = { /* search */ }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                GlassIconButton(onClick = { /* settings */ }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            },
        )

        GlassFloatingActionButton(
            onClick = { dialogOpen = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 120.dp),
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
        }

        GlassNavigationBar(modifier = Modifier.align(Alignment.BottomCenter)) {
            GlassNavigationBarItem(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = "Home",
            )
            GlassNavigationBarItem(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                icon = { Icon(Icons.Default.Search, contentDescription = null) },
                label = "Browse",
            )
            GlassNavigationBarItem(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
                label = "Saved",
            )
            GlassNavigationBarItem(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                label = "More",
            )

        }
    }

    if (dialogOpen) {
        GlassDialog(onDismissRequest = { dialogOpen = false }) {
            Text(
                text = "Confirm action",
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "This dialog itself is rendered as Liquid Glass.",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                GlassButton(
                    text = "Cancel",
                    onClick = { dialogOpen = false },
                    modifier = Modifier.weight(1f),
                )
                GlassButton(
                    text = "Confirm",
                    onClick = { dialogOpen = false },
                    modifier = Modifier.weight(1f),
                    style = GlassStyles.Neon,
                )
            }
        }
    }
}
