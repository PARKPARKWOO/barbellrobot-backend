package com.example.core.user.member.adapter.out.persistence.entity

import jakarta.persistence.Embeddable

@Embeddable
class MemberInfo(
    var tall: Double,
    var weight: Double,
    var skeletalMuscleMass: Double?, // 골격근량
    var age: Int,
    var exerciseMonths: Int,
)
