package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseAreaRepository : JpaRepository<ExerciseAreaEntity, Long>
