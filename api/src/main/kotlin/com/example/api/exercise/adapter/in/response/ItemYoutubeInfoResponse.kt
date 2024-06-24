package com.example.api.exercise.adapter.`in`.response

import com.example.domain.exercise.ItemYoutubeInfo
import com.example.domain.exercise.VideoType

data class ItemYoutubeInfoResponse(
    val id: Long,
    val channel: String,
    val videoType: VideoType,
    val title: String,
    val youtubeUrl: String,
) {
    companion object {
        fun fromDomain(domain: ItemYoutubeInfo): ItemYoutubeInfoResponse = ItemYoutubeInfoResponse(
            id = domain.id,
            channel = domain.channel,
            videoType = domain.videoType,
            title = domain.title,
            youtubeUrl = domain.youtubeUrl,
        )
    }
}
