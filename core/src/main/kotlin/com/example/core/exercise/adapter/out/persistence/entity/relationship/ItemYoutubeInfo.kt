package com.example.core.exercise.adapter.out.persistence.entity.relationship

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseItemEntity
import com.example.domain.exercise.ItemYoutubeInfo
import com.example.domain.exercise.VideoType
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
    val id: Long,
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
