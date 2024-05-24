package com.example.core.user.member.dto

import com.example.domain.user.Gender
import com.querydsl.core.annotations.QueryProjection

data class MemberAndGoalQueryDto
    @QueryProjection
    constructor(
        val goal: List<String>,
        val exerciseMonths: Int,
        val tall: Double,
        val weight: Double,
        val age: Int,
        val gender: Gender,
    )
