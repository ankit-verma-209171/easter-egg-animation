package com.codeitsolo.easteregganimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
    val isEasterEggHatched by remember(Unit) { mutableStateOf(false) }

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
                    .padding(top = 102.dp),
                isEasterEggHatched = isEasterEggHatched
            )

            EasterEgg(
                modifier = Modifier
                    .padding(top = 110.dp),
            )
        }
    }
}

@Composable
private fun EasterEgg(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
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