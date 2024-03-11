package com.example.core.multimedia.application.port.out

interface MediaJpaPort {
    fun saveAllMedia(uri: List<String>): List<Long>
}
