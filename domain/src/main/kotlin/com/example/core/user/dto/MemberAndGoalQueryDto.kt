package com.example.core.user.dto

data class MemberAndGoalQueryDto
constructor(
    val memberDetailQueryDto: MemberDetailQueryDto?,
    val memberGoal: List<String>,
//        val goal: List<String>,
//        val exerciseMonths: Int,
//        val tall: Double,
//        val weight: Double,
//        val age: Int,
//        val gender: Gender,
)
