package com.example.core.exercise.port.command

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity

data class SaveExerciseItemOutCommand(
    val exerciseName: String,
    val videoUri: List<String>,
    val imageUri: List<String>,
    var exerciseAreas: MutableList<ExerciseAreaEntity>,
    var exerciseGoals: MutableList<ExerciseGoalEntity>,
)
