package com.example.core.member.application.`in`.command

data class SignMemberFromEmailCommand(
    val email: String,
    val nickname: String,
    val password: String,
    val gender: String,
    val age: Int,
    val exerciseMonths: Int,
    val tall: Double,
    val weight: Double,
    val skeletalMuscleMass: Double?,
)
