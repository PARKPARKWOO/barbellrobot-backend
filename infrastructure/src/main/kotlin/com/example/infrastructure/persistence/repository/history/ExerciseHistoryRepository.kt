package com.example.infrastructure.persistence.repository.history

import com.example.infrastructure.persistence.entity.history.ExerciseHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseHistoryRepository : JpaRepository<ExerciseHistoryEntity, Long>
