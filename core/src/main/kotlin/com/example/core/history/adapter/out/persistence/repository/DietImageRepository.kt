package com.example.core.history.adapter.out.persistence.repository

import com.example.core.history.adapter.out.persistence.entity.DietImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DietImageRepository : JpaRepository<DietImageEntity, UUID>
