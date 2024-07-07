package com.example.core.exercise.model

data class ItemYoutubeInfo(
    val id: Long,
    val channel: String,
    val videoType: VideoType,
    val title: String,
    val youtubeUrl: String,
)

enum class VideoType {
    YOUTUBE_SHORTS,
    YOUTUBE_NORMAL,
}
