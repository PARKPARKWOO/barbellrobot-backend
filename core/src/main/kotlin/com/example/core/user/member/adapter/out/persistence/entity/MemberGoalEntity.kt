package com.example.core.user.member.adapter.out.persistence.entity

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete

const val MEMBER_GOAL_TABLE_NAME = "member_goal"

@Entity
@Table(name = MEMBER_GOAL_TABLE_NAME)
@SQLDelete(sql = "UPDATE $MEMBER_GOAL_TABLE_NAME SET is_deleted = true WHERE id = ?")
class MemberGoalEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = LAZY)
    val memberEntity: MemberEntity,
    @ManyToOne(fetch = LAZY)
    val exerciseGoalEntity: ExerciseGoalEntity,
    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,
) {
    fun revert() {
        if (this.isDeleted) {
            this.isDeleted = false
        }
    }
}
