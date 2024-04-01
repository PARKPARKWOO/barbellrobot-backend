package com.example.core.user.member.adapter.out.persistence.entity

import jakarta.persistence.Embeddable

@Embeddable
class MemberInfo(
    var tall: Double,
    var weight: Double,
    // 골격근량
    var skeletalMuscleMass: Double?,
    var age: Int,
    var exerciseMonths: Int,
)
