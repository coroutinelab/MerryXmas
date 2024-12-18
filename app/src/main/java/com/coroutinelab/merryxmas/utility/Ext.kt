package com.coroutinelab.merryxmas.utility

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import kotlin.random.Random


fun DrawScope.drawSnowflake(x: Float, y: Float, size: Float, alpha: Float) {
    drawCircle(
        color = Color.White.copy(alpha = alpha),
        radius = size,
        center = Offset(x, y)
    )
}



fun generateMaterialColors(length: Int): List<Color> {
    val materialPalette = listOf(
        Color(0xFFEF5350), // Red
        Color(0xFFFF7043), // Deep Orange
        Color(0xFFFFA726), // Orange
        Color(0xFFFFCA28), // Amber
        Color(0xFFFFEE58), // Yellow
        Color(0xFF66BB6A), // Green
        Color(0xFF26A69A), // Teal
        Color(0xFF42A5F5), // Blue
        Color(0xFF5C6BC0), // Indigo
        Color(0xFFAB47BC), // Purple
        Color(0xFF8D6E63), // Brown
        Color(0xFFBDBDBD)  // Grey
    )

    return List(length) {
        materialPalette[Random.nextInt(materialPalette.size)]
    }
}