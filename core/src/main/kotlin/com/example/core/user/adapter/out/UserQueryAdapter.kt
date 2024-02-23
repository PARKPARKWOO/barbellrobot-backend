package com.example.core.user.adapter.out

import com.example.core.user.UserEntity
import com.example.core.user.application.out.UserQueryPort
import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity
import com.example.core.user.trainer.adapter.out.persistence.entity.QTrainerEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UserQueryAdapter(
    private val jpaQueryFactory: JPAQueryFactory,
) : UserQueryPort {
    override fun findByNickname(nickname: String): UserEntity? {
        val member = jpaQueryFactory.select(QMemberEntity.memberEntity)
            .from(QMemberEntity.memberEntity)
            .where(QMemberEntity.memberEntity.nickname.eq(nickname))
            .fetchOne()
        val trainer = jpaQueryFactory.select(QTrainerEntity.trainerEntity)
            .from(QTrainerEntity.trainerEntity)
            .where(QTrainerEntity.trainerEntity.nickname.eq(nickname))
            .fetchOne()
        val user =
            member?.let {
                member.toUserEntity()
            } ?: trainer?.let {
                trainer.toUserEntity()
            }
        return user
    }
}
