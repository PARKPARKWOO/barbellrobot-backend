package com.example.core.ai.application.port.command

import com.example.core.exercise.model.ExerciseItem
import com.example.core.user.model.Gender

data class CallPtCommand(
    val goal: List<String>,
    val exerciseMonths: Int,
    val tall: Double,
    val weight: Double,
    val age: Int,
    val gender: Gender,
    val day: Int,
    val time: Int,
    val exerciseItemList: List<ExerciseItem>,
)
