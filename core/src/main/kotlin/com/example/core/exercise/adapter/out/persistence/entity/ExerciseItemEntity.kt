package com.example.core.exercise.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

const val EXERCISE_ITEM_TABLE_NAME = "exercise_item"

@Entity
@Table(name = EXERCISE_ITEM_TABLE_NAME)
class ExerciseItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column(name = "exercise_name")
    var exerciseName: String,
    @Column(name = "video_uri")
    var videoUri: String?,
    @Column(name = "image_uri")
    var imageUri: String?,
)
