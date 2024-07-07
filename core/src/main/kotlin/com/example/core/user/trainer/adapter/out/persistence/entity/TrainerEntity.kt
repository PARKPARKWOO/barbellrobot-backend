package com.example.core.user.trainer.adapter.out.persistence.entity

import com.example.core.common.persistence.BaseEntity
import com.example.core.user.model.interfaces.UserEntity
import com.example.core.user.model.Role
import com.example.core.user.model.Trainer
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete

const val TRAINER_TABLE_NAME = "trainer"

@Entity
@Table(name = TRAINER_TABLE_NAME)
@DynamicUpdate
@SQLDelete(sql = "UPDATE $TRAINER_TABLE_NAME set deleted_at = now() WHERE id = ?")
class TrainerEntity(
    @Column(name = "email")
    var email: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "role")
    override var role: Role,
    @Column(name = "profile")
    var profile: String?,
) : BaseEntity(), UserEntity {
    fun toDomain(): Trainer {
        return Trainer(
            id = id,
            password = password,
            email = email,
            role = role,
        )
    }

    override fun toUserEntity(): UserEntity {
        return this
    }

    override fun uploadProfile(uri: String) {
        this.profile = uri
    }
}
