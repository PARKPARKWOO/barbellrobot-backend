package com.example.core.user.member.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.QExerciseGoalEntity.exerciseGoalEntity
import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity.memberEntity
import com.example.core.user.member.adapter.out.persistence.entity.QMemberGoalEntity.memberGoalEntity
import com.example.core.user.member.dto.MemberAndGoalQueryDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

interface MemberRepository : JpaRepository<MemberEntity, UUID>, MemberQueryRepository {
    fun findByNickname(nickname: String): MemberEntity?

    fun findByEmailAndPassword(email: String, password: String): MemberEntity?
}

interface MemberQueryRepository {
    fun findMemberAndGoal(memberId: UUID): MemberAndGoalQueryDto?
}

@Repository
class MemberQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MemberQueryRepository {
    override fun findMemberAndGoal(memberId: UUID): MemberAndGoalQueryDto? {
        val memberDetails = jpaQueryFactory
            .selectFrom(memberEntity)
            .where(memberEntity.id.eq(memberId))
            .fetchOne()

        // Fetch goals
        val goals = jpaQueryFactory
            .select(exerciseGoalEntity.goal)
            .from(memberGoalEntity)
            .leftJoin(memberGoalEntity.exerciseGoalEntity, exerciseGoalEntity)
            .where(memberGoalEntity.memberEntity.id.eq(memberId))
            .fetch()

        return memberDetails?.let {
            MemberAndGoalQueryDto(
                goal = goals,
                exerciseMonths = memberDetails.memberInfo.exerciseMonths,
                tall = memberDetails.memberInfo.tall,
                weight = memberDetails.memberInfo.weight,
                age = memberDetails.memberInfo.age,
                gender = memberDetails.gender,
            )
        }
    }
}
