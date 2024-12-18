package com.coroutinelab.merryxmas.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay

@Composable
fun AnimatedChristmasTree(showWishes: (Boolean) -> Unit) {
    val levels = 6
    val progressStates = remember { List(levels) { mutableFloatStateOf(0f) } }
    var animationComplete by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        for (i in 0 until levels) {
            progressStates[i].floatValue = 1f // Trigger animation for this level
            delay(1000) // Slightly less than the animation duration (1200ms)
        }
        animationComplete = true
        showWishes(true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StarImage(isFilled = animationComplete)
        Spacer(modifier = Modifier.height(8.dp))
        val baseWidth = 200.dp
        for (index in levels - 1 downTo 0) {
            val levelProgress = progressStates[index].floatValue
            val levelWidth = baseWidth - (index * 30).dp
            ChristmasTreeLevel(progress = levelProgress, width = levelWidth)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ChristmasTreeLevel(progress: Float, width: Dp) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1200, easing = LinearEasing), label = ""
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(20.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .background(Color(0xFF4CAF50), RoundedCornerShape(10.dp))
        )
    }
}

@Composable
fun StarImage(isFilled: Boolean) {
    Image(
        imageVector =  Icons.Outlined.Star,
        contentDescription = "Star",
        modifier = Modifier.size(48.dp),
        colorFilter = ColorFilter.tint(if (isFilled) Color.Yellow else Color.Gray)
    )
}