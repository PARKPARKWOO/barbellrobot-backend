package com.example.core.image.application.port.out

interface ImageJpaPort {
    fun saveImage(uri: String): Long

    fun saveImages(uriList: List<String>): List<Long>

    fun delete(vararg id: Long)
}
