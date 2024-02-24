package com.example.core.history.adapter.out.persistence.repository

import com.example.core.history.adapter.out.persistence.entity.ExerciseHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseHistoryRepository : JpaRepository<ExerciseHistoryEntity, Long>
