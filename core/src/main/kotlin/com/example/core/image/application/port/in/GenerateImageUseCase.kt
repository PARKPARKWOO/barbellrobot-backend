package com.example.core.image.application.port.`in`

interface GenerateImageUseCase {
    fun generateImage(): Long

    fun generateImages(): List<Long>
}
