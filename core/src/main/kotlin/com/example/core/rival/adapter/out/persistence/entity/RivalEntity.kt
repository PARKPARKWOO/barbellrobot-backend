package com.example.core.rival.adapter.out.persistence.entity

import com.example.core.common.persistence.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.UUID

@Entity
class RivalEntity(
    @Column(name = "member_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    val memberId: UUID,
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    val myRivals: MutableList<RivalStatusEntity> = mutableListOf(),
) : BaseEntity() {
    fun addRival(rival: RivalStatusEntity) {
        myRivals.add(rival)
    }

    fun deleteRival(rival: RivalStatusEntity) {
        this.myRivals.remove(rival)
    }
}
