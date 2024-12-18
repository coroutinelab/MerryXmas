package com.coroutinelab.merryxmas

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coroutinelab.merryxmas.theme.TestUITheme
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.coroutinelab.merryxmas.component.AnimatedChristmasTree
import com.coroutinelab.merryxmas.component.AnimatedText
import com.coroutinelab.merryxmas.component.Santa
import com.coroutinelab.merryxmas.model.generateSnowflakes
import com.coroutinelab.merryxmas.utility.drawSnowflake

class MainActivity : ComponentActivity() {
    lateinit var mp: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mp = MediaPlayer.create(this, R.raw.jingle)

        setContent {
            TestUITheme {
                val showSnow by remember { mutableStateOf(true) }
                val alpha by animateFloatAsState(
                    targetValue = if (showSnow) 1f else 0f,
                    animationSpec = tween(durationMillis = 2000), label = ""
                )

                if (alpha > 0f) {
                    SnowCanvas(alpha)
                }
                mp.start()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        mp.stop()
    }
}

@Composable
fun SnowCanvas(alpha: Float) {
    val configuration = LocalConfiguration.current
    val screenHeight = with(LocalDensity.current) { configuration.screenHeightDp.dp.toPx() }
    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }

    val snowflakes = remember { generateSnowflakes(20, screenHeight, screenWidth) }
    val transition = rememberInfiniteTransition(label = "")
    var showXmasWish by remember { mutableStateOf(false) }

    val animatedOffsets = snowflakes.map { snowflake ->
        transition.animateFloat(
            initialValue = snowflake.startY,
            targetValue = snowflake.endY,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = snowflake.duration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = ""
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(Color.Black.copy(alpha = .8f))
            snowflakes.forEachIndexed { index, snowflake ->
                val x = snowflake.x
                val y = animatedOffsets[index].value
                drawSnowflake(x, y, snowflake.size, alpha)
            }
        }

        Column {
            Santa()
            Spacer(modifier = Modifier.weight(1f))
            if(showXmasWish) {
                AnimatedText(
                    text = "Merry Christmas",
                )
            }

            AnimatedChristmasTree {
                showXmasWish = it
            }
        }
    }
}