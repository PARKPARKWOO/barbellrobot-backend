package com.example.core.user.member.adapter.out.persistence.entity

import com.example.core.common.persistence.BaseEntity
import com.example.core.user.UserEntity
import com.example.domain.user.Member
import com.example.domain.user.Provider
import com.example.domain.user.Role
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete

const val MEMBER_TABLE_NAME = "member"

@Entity
@Table(name = MEMBER_TABLE_NAME)
@SQLDelete(sql = "UPDATE $MEMBER_TABLE_NAME set deleted_at = now() WHERE id = ?")
@DynamicUpdate
class MemberEntity(
    @Column(name = "email", nullable = true)
    var email: String?,
    @Column(name = "password", nullable = true)
    var password: String?,
    @AttributeOverrides(
        AttributeOverride(name = "social_id", column = Column(name = "social_id")),
        AttributeOverride(name = "provider", column = Column(name = "provider")),
    )
    var socialProvider: SocialProvider?,
    @Column(name = "role")
    @Enumerated(STRING)
    override var role: Role,
    @Column(name = "profile", nullable = true)
    var profile: String?,
) : BaseEntity(), UserEntity {
    fun toDomain(): Member {
        return Member(
            id = this.id,
            email = this.email,
            password = this.password,
            socialProvider = socialProvider?.let {
                com.example.domain.user.SocialProvider(it.socialId, it.provider)
            },
            createdAt = this.createdAt,
            deletedAt = this.deletedAt,
            role = this.role,
            profile = this.profile,
        )
    }

    override fun toUserEntity(): UserEntity {
        return this
    }

    override fun uploadProfile(uri: String) {
        this.profile = uri
    }
}

@Embeddable
data class SocialProvider(
    @Column(name = "social_id")
    val socialId: String,
    @Enumerated(STRING)
    val provider: Provider,
)
