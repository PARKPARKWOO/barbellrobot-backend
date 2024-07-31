package com.example.infrastructure.persistence.entity.rival

import com.example.core.rival.model.Rival
import com.example.infrastructure.common.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.UUID

const val RIVAL_ENTITY_TABLE_NAME = "rival"

@Entity
@Table(name = RIVAL_ENTITY_TABLE_NAME)
class RivalEntity(
    @Column(name = "member_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    val memberId: UUID,
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "my_rival_list")
    val myRivals: MutableList<RivalCurrentSituationEntity> = mutableListOf(),
) : BaseEntity() {
    fun addRival(rival: RivalCurrentSituationEntity) {
        myRivals.add(rival)
    }

    fun deleteRival(rival: RivalCurrentSituationEntity) {
        this.myRivals.remove(rival)
    }

    fun toModel(): Rival = Rival(
        memberId = memberId,
        myRivals = myRivals.map { it.toModel() },
    )
}
