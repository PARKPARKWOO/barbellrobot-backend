package com.example.core.pt.adapter.out.persistence.repository

import com.example.core.pt.adapter.out.persistence.entity.AiPtEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AiPtRepository : JpaRepository<AiPtEntity, UUID>
