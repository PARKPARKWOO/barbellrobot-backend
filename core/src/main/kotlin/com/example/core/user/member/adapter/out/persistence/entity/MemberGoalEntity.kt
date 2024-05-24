package com.example.core.user.member.adapter.out.persistence.entity

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

const val MEMBER_GOAL_TABLE_NAME = "member_goal"

@Entity
@Table(name = MEMBER_GOAL_TABLE_NAME)
class MemberGoalEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = LAZY)
    val memberEntity: MemberEntity,
    @ManyToOne(fetch = LAZY)
    val exerciseGoalEntity: ExerciseGoalEntity,
)
