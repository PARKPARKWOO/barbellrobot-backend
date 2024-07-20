package com.example.core.multimedia.port.out

interface MediaJpaPort {
    fun saveAllMedia(uri: List<String>): List<Long>
}
