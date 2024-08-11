package com.example.`in`.api.exercise.adapter.`in`.response

import com.example.core.exercise.model.ItemYoutubeInfo
import com.example.core.exercise.model.VideoType

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
