package com.example.common.generate

import java.util.UUID
import kotlin.random.Random

object GenerateRandom {
    fun generateNumber(from: Int, until: Int): Int {
        return Random.nextInt(from, until)
    }

    fun generateUUID(): UUID {
        return UUID.randomUUID()
    }
}
