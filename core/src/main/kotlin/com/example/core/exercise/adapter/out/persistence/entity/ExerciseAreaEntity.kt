package com.example.core.exercise.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

const val EXERCISE_CATEGORY_TABLE_NAME = "exercise_area"

@Entity
@Table(name = EXERCISE_CATEGORY_TABLE_NAME)
class ExerciseAreaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @Column(name = "area")
    var area: String,
)
