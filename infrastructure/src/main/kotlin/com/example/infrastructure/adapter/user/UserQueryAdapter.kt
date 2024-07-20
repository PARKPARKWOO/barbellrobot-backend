package com.example.infrastructure.adapter.user

import com.example.core.user.model.interfaces.UserEntity
import com.example.core.user.model.interfaces.UserHealthDetail
import com.example.core.user.port.out.UserQueryPort
import com.example.infrastructure.persistence.entity.member.QMemberEntity
import com.example.infrastructure.persistence.entity.member.QMemberInfo.memberInfo
import com.example.infrastructure.persistence.entity.trainer.QTrainerEntity
import com.example.infrastructure.persistence.entity.trainer.QTrainerInfo.trainerInfo
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserQueryAdapter(
    private val jpaQueryFactory: JPAQueryFactory,
) : UserQueryPort {
    override fun findByNickname(nickname: String): UserHealthDetail? = jpaQueryFactory
        .select(memberInfo)
        .from(memberInfo)
        .where(memberInfo.nickname.eq(nickname))
        .fetchOne()
        ?: jpaQueryFactory
            .select(trainerInfo)
            .from(trainerInfo)
            .where(trainerInfo.nickname.eq(nickname))
            .fetchOne()

    override fun findById(userId: UUID): UserEntity? {
        return jpaQueryFactory
            .select(QMemberEntity.memberEntity)
            .from(QMemberEntity.memberEntity)
            .where(QMemberEntity.memberEntity.id.eq(userId))
            .fetchOne()?.toUserEntity()
            ?: jpaQueryFactory.select(QTrainerEntity.trainerEntity)
                .from(QTrainerEntity.trainerEntity)
                .where(QTrainerEntity.trainerEntity.id.eq(userId))
                .fetchOne()?.toUserEntity()
    }

    override fun findByEmail(email: String): UserEntity? {
        return jpaQueryFactory
            .select(QMemberEntity.memberEntity)
            .from(QMemberEntity.memberEntity)
            .where(QMemberEntity.memberEntity.email.eq(email))
            .fetchOne()?.toUserEntity()
            ?: jpaQueryFactory.select(QTrainerEntity.trainerEntity)
                .from(QTrainerEntity.trainerEntity)
                .where(QTrainerEntity.trainerEntity.email.eq(email))
                .fetchOne()?.toUserEntity()
    }
}
