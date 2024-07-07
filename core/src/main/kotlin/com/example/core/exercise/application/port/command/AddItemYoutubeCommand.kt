package com.example.core.exercise.application.port.command

import com.example.core.exercise.model.VideoType

data class AddItemYoutubeCommand(
    val channel: String,
    val videoType: VideoType,
    val title: String,
    val youtubeUrl: String,
    val itemId: Long,
)
