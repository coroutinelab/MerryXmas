package com.coroutinelab.merryxmas.model

import kotlin.random.Random

data class Snowflake(
    val x: Float,
    val startY: Float,
    val endY: Float,
    val size: Float,
    val duration: Int
)

fun generateSnowflakes(
    count: Int,
    screenHeight: Float,
    screenWidthDp: Float
) = List(count) {
    Snowflake(
        x = Random.nextFloat() * screenWidthDp,
        startY = -50f,
        endY = screenHeight,
        size = Random.nextFloat() * 10 + 5,
        duration = Random.nextInt(1000, 7000)
    )
}