package com.example.core.exercise.adapter.out.persistence.entity

import com.example.domain.exercise.ExerciseArea
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

const val EXERCISE_AREA_TABLE_NAME = "exercise_area"
const val EXERCISE_AREA_ID = EXERCISE_AREA_TABLE_NAME + "_id"

@Entity
@Table(name = EXERCISE_AREA_TABLE_NAME)
class ExerciseAreaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(name = "area")
    var area: String,
) {
    fun toDomain(): ExerciseArea {
        return ExerciseArea(
            id = this.id,
            area = this.area,
        )
    }
}
