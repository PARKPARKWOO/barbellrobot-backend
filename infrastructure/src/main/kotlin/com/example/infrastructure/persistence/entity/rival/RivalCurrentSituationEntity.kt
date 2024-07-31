package com.example.infrastructure.persistence.entity.rival

import com.example.core.rival.model.RivalCurrentSituation
import com.example.core.rival.model.RivalStatus
import com.example.infrastructure.common.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.UUID

// status 는 아닌듯
@Entity
class RivalCurrentSituationEntity(
    @Column(name = "sender")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    val sender: UUID,
    @Column(name = "receiver")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    val receiver: UUID,
    @Enumerated(STRING)
    var rivalStatus: RivalStatus,
) : BaseEntity() {
    companion object {
        fun from(domain: RivalCurrentSituation): RivalCurrentSituationEntity = RivalCurrentSituationEntity(
            sender = domain.sender,
            receiver = domain.receiver,
            rivalStatus = domain.rivalStatus,
        )
    }

    fun refuseRival() {
        this.rivalStatus = RivalStatus.REFUSE
    }

    fun acceptRival() {
        this.rivalStatus = RivalStatus.ACTIVE
    }

    fun toModel(): RivalCurrentSituation = RivalCurrentSituation(
        sender = sender,
        receiver = receiver,
        rivalStatus = rivalStatus,
    )
}
