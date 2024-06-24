package com.example.api.exercise.adapter.`in`.request

import com.example.core.exercise.application.port.command.AddItemYoutubeCommand
import com.example.domain.exercise.VideoType

data class AddYoutubeRequest(
    val itemId: Long,
    val videoType: VideoType,
    val channel: String,
    val title: String,
    val youtubeUrl: String,
) {
    fun toCommand(): AddItemYoutubeCommand = AddItemYoutubeCommand(
        itemId = itemId,
        channel = channel,
        videoType = videoType,
        title = title,
        youtubeUrl = youtubeUrl,
    )
}
