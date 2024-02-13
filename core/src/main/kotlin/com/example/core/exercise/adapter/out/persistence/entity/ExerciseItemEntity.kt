package com.example.core.exercise.adapter.out.persistence.entity

import com.example.domain.exercise.ExerciseItem
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table

const val EXERCISE_ITEM_TABLE_NAME = "exercise_item"

@Entity
@Table(name = EXERCISE_ITEM_TABLE_NAME)
class ExerciseItemEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(name = "exercise_name")
    var exerciseName: String,
    @Column(name = "video_uri")
    var videoUri: String?,
    @Column(name = "image_uri")
    var imageUri: String?,
    @ElementCollection(fetch = LAZY)
    @CollectionTable(name = "exercise_area_ids", joinColumns = [JoinColumn(name = "exercise_item_id")])
    var exerciseAreas: MutableList<Long> = mutableListOf(),
    @ElementCollection(fetch = LAZY)
    @CollectionTable(name = "exercise_goal_ids", joinColumns = [JoinColumn(name = "exercise_item_id")])
    var exerciseGoals: MutableList<Long> = mutableListOf(),
) {
    fun addExerciseArea(exerciseAreaId: Long) {
        this.exerciseAreas.add(exerciseAreaId)
    }

    fun addExerciseGoal(exerciseGoalId: Long) {
        this.exerciseGoals.add(exerciseGoalId)
    }

    fun removeExerciseGoal(exerciseGoalId: Long) {
        this.exerciseGoals.remove(exerciseGoalId)
    }

    fun removeExerciseArea(exerciseAreaId: Long) {
        this.exerciseAreas.remove(exerciseAreaId)
    }

    fun changeVideoUri(uri: String) {
        this.videoUri = uri
    }

    fun changeImageUri(uri: String) {
        this.imageUri = uri
    }

    fun toDomain(): ExerciseItem {
        return ExerciseItem(
            id = id,
            exerciseName = exerciseName,
            videoUri = videoUri,
            imageUri = imageUri,
            exerciseAreas = exerciseAreas,
            exerciseGoals = exerciseGoals,
        )
    }
}
