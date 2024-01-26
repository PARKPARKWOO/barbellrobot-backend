package com.example.core.user.adapter.out.persistence

import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity.memberEntity
import com.example.core.user.trainer.adapter.out.persistence.entity.QTrainerEntity.trainerEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

interface UserQueryRepository {
    fun findNickname(nickname: String): Boolean
}

@Repository
class UserQueryRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : UserQueryRepository {
    override fun findNickname(nickname: String): Boolean {
        val memberQuery = jpaQueryFactory.select(memberEntity)
            .from(memberEntity)
            .where(memberEntity.nickname.eq(nickname))
        val trainerQuery = jpaQueryFactory.select(trainerEntity)
            .from(trainerEntity)
            .where(trainerEntity.nickname.eq(nickname))
        val findUser = jpaQueryFactory.selectOne().where(
            memberQuery.exists().or(trainerQuery.exists()),
        ).exists()
        return findUser != null
    }
}
