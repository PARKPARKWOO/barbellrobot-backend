package com.example.core.exercise.port.out

import com.example.core.exercise.port.command.AddItemYoutubeCommand

interface ItemYoutubeJpaPort {
    fun add(command: AddItemYoutubeCommand)

    fun delete(id: Long)
}
