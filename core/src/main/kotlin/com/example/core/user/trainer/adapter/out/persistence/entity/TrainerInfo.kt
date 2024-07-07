package com.example.core.user.trainer.adapter.out.persistence.entity

import com.example.core.user.model.interfaces.UserHealthDetail
import com.example.core.user.model.Gender
import com.example.core.user.model.TrainerInfo
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes.VARCHAR
import java.util.UUID

@Entity
@Table(name = "trainer_info")
class TrainerInfo(
    @Id
    @Column(name = "trainer_id", columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(VARCHAR)
    override val userId: UUID,
    @Column(name = "gym_name")
    var gymName: String,
    @Column(name = "nickname")
    var nickname: String,
    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "street", column = Column(name = "street")),
        AttributeOverride(name = "city", column = Column(name = "city")),
        AttributeOverride(name = "country", column = Column(name = "country")),
    )
    var gymAddress: GymAddress,
    @Enumerated(STRING)
    @Column(name = "gender")
    var gender: Gender,
    @Column(name = "exercise_years")
    var exerciseYears: Int,
    @Column(name = "introduce")
    var introduce: String?,
) : UserHealthDetail {
    fun toDomain(): TrainerInfo = TrainerInfo(
        gymName = gymName,
        street = gymAddress.street,
        city = gymAddress.city,
        country = gymAddress.country,
        exerciseYears = exerciseYears,
        introduce = introduce,
        gender = gender,
        nickname = nickname,
    )

    override fun toModel(): UserHealthDetail = this
}

@Embeddable
data class GymAddress(
    val street: String,
    val city: String,
    val country: String,
)
