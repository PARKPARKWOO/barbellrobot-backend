package com.example.infrastructure.persistence.repository.history

import com.example.infrastructure.persistence.entity.history.DietFoodEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DietFoodRepository : JpaRepository<DietFoodEntity, UUID>
