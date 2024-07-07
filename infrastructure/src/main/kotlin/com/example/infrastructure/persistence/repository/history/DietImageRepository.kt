package com.example.infrastructure.persistence.repository.history

import com.example.infrastructure.persistence.entity.history.DietImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DietImageRepository : JpaRepository<DietImageEntity, UUID>
