package com.example.core.user.member.adapter.out.persistence.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.util.UUID

const val MEMBER_GOAL_TABLE_NAME = "member_goal"

@Entity
@Table(name = MEMBER_GOAL_TABLE_NAME)
class MemberGoalEntity(
    @Id
    @Column(name = "id")
    val memberId: UUID,
    @Column(name = "goal_ids")
    @ElementCollection
    @CollectionTable(name = "member_goals", joinColumns = [JoinColumn(name = "member_goal_id")])
    val exerciseGoalIds: MutableList<Long> = mutableListOf(),
) {
    fun addGoal(goals: List<Long>) {
        exerciseGoalIds.addAll(goals)
    }
}
