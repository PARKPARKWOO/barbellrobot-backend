package com.example.common.generate

import kotlin.random.Random

object GenerateRandom {
    fun generateNumber(from: Int, until: Int): Int {
        return Random.nextInt(from, until)
    }
}
