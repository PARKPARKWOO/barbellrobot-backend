package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseGoalRepository : JpaRepository<ExerciseGoalEntity, Long>
