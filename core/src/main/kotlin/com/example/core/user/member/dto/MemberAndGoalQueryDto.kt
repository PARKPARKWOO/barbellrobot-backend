package com.example.core.user.member.dto

import com.querydsl.core.annotations.QueryProjection

data class MemberAndGoalQueryDto
    @QueryProjection
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
