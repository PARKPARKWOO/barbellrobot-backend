package com.example.core.user.trainer.adapter.out.persistence.entity

import com.example.core.common.persistence.BaseEntity
import com.example.domain.user.Gender
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete

const val TRAINER_TABLE_NAME = "trainer"

@Entity
@Table(name = TRAINER_TABLE_NAME)
@DynamicUpdate
@SQLDelete(sql = "UPDATE $TRAINER_TABLE_NAME set deleted_at = now() WHERE id = ?")
class TrainerEntity(
    @Column(name = "nickname")
    var nickname: String,
    @Column(name = "email")
    var email: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "gym_name")
    var gymName: String,
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "street", column = Column(name = "street")),
        AttributeOverride(name = "city", column = Column(name = "city")),
        AttributeOverride(name = "country", column = Column(name = "country")),
    )
    var gymAddress: GymAddress,
    @Column(name = "exercise_years")
    var exerciseYears: Int,
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    var gender: Gender,
    @Column(name = "introduce")
    var introduce: String,
) : BaseEntity() {
    fun changeGym(gymName: String, gymAddress: GymAddress) {
        this.gymName = gymName
        this.gymAddress = gymAddress
    }

    fun changeIntroduce(introduce: String) {
        this.introduce = introduce
    }
}

@Embeddable
data class GymAddress(
    val street: String,
    val city: String,
    val country: String,
)
