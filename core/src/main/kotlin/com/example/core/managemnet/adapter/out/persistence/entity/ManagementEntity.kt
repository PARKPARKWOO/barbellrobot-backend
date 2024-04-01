package com.example.core.managemnet.adapter.out.persistence.entity

import com.example.domain.management.Management
import com.example.domain.management.ManagementStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.SQLDelete
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

const val MANAGEMENT_TABLE_NAME = "management"

@Entity
@Table(name = MANAGEMENT_TABLE_NAME)
@DynamicUpdate
@SQLDelete(sql = "update $MANAGEMENT_TABLE_NAME set deleted_at = now(), status = 'REJECT' WHERE id = ?")
class ManagementEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(name = "trainer_id")
    val trainerId: UUID,
    @Column(name = "member_id")
    val memberId: UUID,
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: ManagementStatus,
    @Column(name = "ended_at")
    var endDate: LocalDate,
) {
    @Column(name = "created_at")
    @CreatedDate
    @NotNull
    lateinit var createdAt: LocalDateTime

    @Column(name = "updated_at")
    @LastModifiedDate
    @NotNull
    lateinit var updatedAt: LocalDateTime

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime? = null

    fun toDomain() = Management(
        id = id,
        trainerId = trainerId,
        memberId = memberId,
        status = status,
        updatedAt = updatedAt,
        endDate = endDate,
    )

    fun update(management: Management) {
        this.status = management.status
        this.endDate = management.endDate
    }
}
