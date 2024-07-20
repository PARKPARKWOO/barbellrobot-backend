package com.example.infrastructure.persistence.repository.trainer

import com.example.infrastructure.persistence.entity.trainer.TrainerEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TrainerRepository : JpaRepository<TrainerEntity, UUID> {
    fun findByEmailAndPassword(email: String, password: String): TrainerEntity?
}
