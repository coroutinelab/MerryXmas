package com.coroutinelab.merryxmas.component

import androidx.compose.animation.core.EaseInOutExpo
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coroutinelab.merryxmas.R
import com.coroutinelab.merryxmas.utility.generateMaterialColors
import kotlinx.coroutines.delay

val xmasFont = FontFamily(
    Font(R.font.xms, FontWeight.Light),
)

@Composable
fun AnimatedText(
    text: String,
    animationDelay: Long = 150L
) {
    val visibleCharacters = remember { mutableIntStateOf(0) }
    val colors = remember { generateMaterialColors(text.length) }
    LaunchedEffect(Unit) {
        for (i in 1..text.length) {
            visibleCharacters.intValue = i
            delay(animationDelay)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        text.forEachIndexed { index, char ->
            if (index < visibleCharacters.intValue) {
                ZoomingCharacter(char, colors[index])
            }
        }
    }
}


@Composable
private fun ZoomingCharacter(char: Char, color: Color) {
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { startAnimation = true }
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.5f,
        animationSpec = tween(
            durationMillis = 1500,
            easing = EaseInOutExpo
        ), label = ""
    )

    Text(
        style = TextStyle(fontSize = 48.sp),
        color = color,
        text = char.toString(),
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }

            .padding(horizontal = 2.dp),
        fontFamily = xmasFont, fontWeight = FontWeight.Light
    )
}
