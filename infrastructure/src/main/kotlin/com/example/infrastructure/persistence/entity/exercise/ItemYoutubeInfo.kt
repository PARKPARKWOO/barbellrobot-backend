package com.example.infrastructure.persistence.entity.exercise

import com.example.core.exercise.model.VideoType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class ItemYoutubeInfo(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = LAZY)
    val item: ExerciseItemEntity,
    @Enumerated(STRING)
    val videoType: VideoType,
    @Column(name = "title")
    val title: String,
    @Column(name = "channel")
    val channel: String,
    @Column(name = "youtube_url")
    val youtubeUrl: String,
) {
    fun toDomain(): ItemYoutubeInfo = ItemYoutubeInfo(
        id = id,
        title = title,
        channel = channel,
        videoType = videoType,
        youtubeUrl = youtubeUrl,
    )
}
