package com.example.core.exercise.port.command

import com.example.core.exercise.model.ExerciseArea
import com.example.core.exercise.model.ExerciseGoal

data class SaveExerciseItemOutCommand(
    val exerciseName: String,
    val videoUri: List<String>,
    val imageUri: List<String>,
    var exerciseAreas: MutableList<ExerciseArea>,
    var exerciseGoals: MutableList<ExerciseGoal>,
)
