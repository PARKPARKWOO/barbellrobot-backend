package com.example.core.exercise.port.command

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import org.springframework.web.multipart.MultipartFile

data class SaveExerciseItemCommand(
    val exerciseName: String,
    val video: List<MultipartFile>?,
    val image: List<MultipartFile>?,
    var exerciseAreas: MutableList<Long>,
    var exerciseGoals: MutableList<Long>,
) {
    fun toOutCommand(
        exerciseAreas: MutableList<ExerciseAreaEntity>,
        exerciseGoals: MutableList<ExerciseGoalEntity>,
        video: MutableList<String>,
        image: MutableList<String>,
    ): SaveExerciseItemOutCommand {
        return SaveExerciseItemOutCommand(
            exerciseName = exerciseName,
            videoUri = video,
            imageUri = image,
            exerciseAreas = exerciseAreas,
            exerciseGoals = exerciseGoals,
        )
    }
}
