package com.example.core.exercise.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

const val EXERCISE_LIST_TABLE_NAME = "exercise_list"

@Entity
@Table(name = EXERCISE_LIST_TABLE_NAME)
class ExerciseList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column(name = "name")
    var name: String,
)
