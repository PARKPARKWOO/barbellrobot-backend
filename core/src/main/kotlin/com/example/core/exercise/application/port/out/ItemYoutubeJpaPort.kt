package com.example.core.exercise.application.port.out

import com.example.core.exercise.application.port.command.AddItemYoutubeCommand

interface ItemYoutubeJpaPort {
    fun add(command: AddItemYoutubeCommand)

    fun delete(id: Long)
}
