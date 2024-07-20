package com.example.core.exercise.port.command

import com.example.core.exercise.model.ExerciseArea
import com.example.core.exercise.model.ExerciseGoal
import com.example.core.multimedia.dto.MultimediaDto

data class SaveExerciseItemCommand(
    val exerciseName: String,
    val video: List<MultimediaDto>?,
    val image: List<MultimediaDto>?,
    var exerciseAreas: MutableList<Long>,
    var exerciseGoals: MutableList<Long>,
) {
    fun toOutCommand(
        exerciseAreas: MutableList<ExerciseArea>,
        exerciseGoals: MutableList<ExerciseGoal>,
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
