package com.example.core.ai.application.port.command

import com.example.domain.user.Gender

data class CallPtCommand(
    val goal: List<String>,
    val exerciseMonths: Int,
    val tall: Double,
    val weight: Double,
    val age: Int,
    val gender: Gender,
    val day: Int,
    val time: Int,
)
