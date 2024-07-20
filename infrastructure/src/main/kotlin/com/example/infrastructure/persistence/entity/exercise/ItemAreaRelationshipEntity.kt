package com.example.infrastructure.persistence.entity.exercise

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

const val ITEM_AREA_RELATIONSHIP_TABLE_NAME = "item_area"

@Entity
@Table(name = ITEM_AREA_RELATIONSHIP_TABLE_NAME)
class ItemAreaRelationshipEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(name = EXERCISE_ITEM_ID)
    var exerciseItemId: Long,
    @Column(name = EXERCISE_AREA_ID)
    var exerciseAreaId: Long,
)
