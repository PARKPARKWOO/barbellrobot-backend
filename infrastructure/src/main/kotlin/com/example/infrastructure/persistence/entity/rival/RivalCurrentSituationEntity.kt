package com.example.infrastructure.persistence.entity.rival

import com.example.core.rival.model.RivalCurrentSituation
import com.example.core.rival.model.RivalStatus
import com.example.infrastructure.common.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.UUID

const val RIVAL_CURRENT_SITUATION_TABLE_NAME = "rival_current_situation"

@Entity
@Table(name = RIVAL_CURRENT_SITUATION_TABLE_NAME)
class RivalCurrentSituationEntity(
    @Column(name = "rival_member_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    val rivalMemberId: UUID,
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "rival")
    val rivalEntity: RivalEntity,
    @Enumerated(STRING)
    var rivalStatus: RivalStatus,
) : BaseEntity() {
    fun refuseRival() {
        this.rivalStatus = RivalStatus.REFUSE
    }

    fun acceptRival() {
        this.rivalStatus = RivalStatus.ACTIVE
    }

    fun toModel(): RivalCurrentSituation = RivalCurrentSituation(
        rivalMemberId = rivalMemberId,
        rivalStatus = rivalStatus,
    )
}
