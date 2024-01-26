package com.example.core.user.trainer.adapter.out.persistence.repository

import com.example.core.user.trainer.adapter.out.persistence.entity.TrainerEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TrainerRepository : JpaRepository<TrainerEntity, UUID>
