package com.example.core.user.adapter.out

import com.example.core.user.UserEntity
import com.example.core.user.application.out.UserQueryPort
import com.example.core.user.member.adapter.out.persistence.entity.QMemberEntity
import com.example.core.user.trainer.adapter.out.persistence.entity.QTrainerEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserQueryAdapter(
    private val jpaQueryFactory: JPAQueryFactory,
) : UserQueryPort {
    override fun findByNickname(nickname: String): UserEntity? {
        return jpaQueryFactory.select(QMemberEntity.memberEntity)
            .from(QMemberEntity.memberEntity)
            .where(QMemberEntity.memberEntity.nickname.eq(nickname))
            .fetchOne()?.toUserEntity()
            ?: jpaQueryFactory.select(QTrainerEntity.trainerEntity)
                .from(QTrainerEntity.trainerEntity)
                .where(QTrainerEntity.trainerEntity.nickname.eq(nickname))
                .fetchOne()?.toUserEntity()
    }

    override fun findById(userId: UUID): UserEntity? {
        return jpaQueryFactory.select(QMemberEntity.memberEntity)
            .from(QMemberEntity.memberEntity)
            .where(QMemberEntity.memberEntity.id.eq(userId))
            .fetchOne()?.toUserEntity()
            ?: jpaQueryFactory.select(QTrainerEntity.trainerEntity)
                .from(QTrainerEntity.trainerEntity)
                .where(QTrainerEntity.trainerEntity.id.eq(userId))
                .fetchOne()?.toUserEntity()
    }
}
