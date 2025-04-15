package com.codeitsolo.easteregganimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeitsolo.easteregganimation.ui.theme.EasterEggAnimationTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb()
            ),
        )
        setContent {
            EasterEggAnimationTheme {
                EasterEggApp()
            }
        }
    }
}

val nunitoFont = FontFamily(
    Font(R.font.nunito_extra_bold, FontWeight.ExtraBold),
    Font(R.font.nunito_black, FontWeight.Black),
)

val easterEggNotReadyBackgroundColors = listOf(
    Color(0xFF8D8EA1),
    Color(0xFF8D8EA1)
)

val easterEggHatchedBackgroundColors = listOf(
    Color(0xFFFFC441),
    Color(0xFFEDA616),
    Color(0xFFE09723),
    Color(0xFFCA7500)
)

@Composable
fun EasterEggApp(
    modifier: Modifier = Modifier
) {
    var isEasterEggShaking by remember(Unit) { mutableStateOf(false) }
    var isEasterEggHatched by remember(Unit) { mutableStateOf<Boolean>(false) }
    var isToastVisible by remember(Unit) { mutableStateOf<Boolean>(false) }
    val scope = rememberCoroutineScope()
    var toastJob by remember { mutableStateOf<Job?>(null) }
    val toastAlpha by animateFloatAsState(
        targetValue = if (isToastVisible) 1f else 0f,
        label = "toastAlpha",
    )

    fun shakeEasterEgg() {
        scope.launch {
            isEasterEggShaking = false
            delay((4..6).random().seconds)
            isEasterEggShaking = true
        }
    }

    fun showToast(isEasterEggReadyToHatch: Boolean) {
        toastJob?.cancel()
        toastJob = scope.launch {
            isToastVisible = true
            isEasterEggHatched = isEasterEggReadyToHatch
            delay(2.seconds)
            isToastVisible = false
        }
    }

    LaunchedEffect(Unit) {
        shakeEasterEgg()
    }

    Scaffold(
        modifier = modifier
            .background(Color(0xFF0F1241))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x0005061D),
                        Color(0xFF05061D)
                    )
                )
            ),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Title(
                modifier = Modifier
                    .padding(top = 24.dp),
            )

            Toast(
                modifier = Modifier
                    .alpha(alpha = toastAlpha)
                    .padding(top = 102.dp),
                isEasterEggHatched = isEasterEggHatched
            )

            EasterEgg(
                modifier = Modifier
                    .padding(top = 110.dp),
                isEasterEggShaking = isEasterEggShaking,
                onClick = {
                    showToast(isEasterEggReadyToHatch = isEasterEggShaking)
                    if (!isEasterEggShaking) return@EasterEgg
                    shakeEasterEgg()
                }
            )
        }
    }
}

@Composable
private fun EasterEgg(
    modifier: Modifier = Modifier,
    isEasterEggShaking: Boolean = true,
    onClick: () -> Unit,
) {
    val rotation = remember { Animatable(0f) }
    val offsetX = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isEasterEggShaking) {
        if (isEasterEggShaking) {
            rotation.snapTo(0f)
            offsetX.snapTo(0f)
            scale.snapTo(1f)

            launch {
                while (isActive) {
                    rotation.animateTo(
                        targetValue = 10f,
                        animationSpec = tween(120, easing = LinearEasing),
                        initialVelocity = 1000f
                    )
                    rotation.animateTo(
                        targetValue = -10f,
                        animationSpec = tween(240, easing = LinearEasing),
                    )
                    rotation.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(120, easing = LinearEasing),
                    )
                }
                rotation.snapTo(0f)
            }

            launch {
                offsetX.animateTo(
                    targetValue = 10f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(600, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    initialVelocity = 1000f
                )
                offsetX.snapTo(0f)
            }

            launch {
                scale.animateTo(
                    targetValue = 1.1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(600, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    initialVelocity = 1000f
                )
                scale.snapTo(0f)
            }
        }
    }

    Image(
        modifier = modifier
            .graphicsLayer {
                rotationZ = rotation.value
                translationX = offsetX.value
                scaleY = scale.value
            }
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        painter = painterResource(R.drawable.img_easter_egg),
        contentDescription = null,
    )
}

@Composable
private fun Toast(
    modifier: Modifier = Modifier,
    isEasterEggHatched: Boolean
) {
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = if (isEasterEggHatched) {
                        easterEggHatchedBackgroundColors
                    } else {
                        easterEggNotReadyBackgroundColors
                    }
                )
            )
            .padding(16.dp),
        text = if (isEasterEggHatched) {
            "You’ve hatched it!"
        } else {
            "Easter egg not ready yet!"
        },
        color = Color.White,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 18.sp,
        fontSize = 20.sp,
        fontFamily = nunitoFont,
    )
}

@Composable
private fun Title(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "Shaky Egg",
        style = TextStyle(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFFFFD343),
                    Color(0xFFFFD64D),
                    Color(0xFFFFB619),
                ),
            ),
            fontWeight = FontWeight.Black,
            lineHeight = 24.sp,
            fontSize = 24.sp,
            fontFamily = nunitoFont,
        ),
    )
}

// region Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EasterEggAppPreview() {
    EasterEggAnimationTheme {
        EasterEggApp()
    }
}

// endregion