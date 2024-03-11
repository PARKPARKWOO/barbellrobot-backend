package com.example.core.multimedia.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

const val MULTIMEDIA_TABLE_NAME = "multimedia"

@Entity
@Table(name = MULTIMEDIA_TABLE_NAME)
class MultimediaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(name = "uri")
    var uri: String,
)
