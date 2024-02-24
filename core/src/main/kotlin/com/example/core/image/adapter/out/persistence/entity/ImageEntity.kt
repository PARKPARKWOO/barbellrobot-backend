package com.example.core.image.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

const val IMAGE_TABLE_NAME = "image"

@Entity
@Table(name = IMAGE_TABLE_NAME)
class ImageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    @Column(name = "uri")
    var uri: String,
)
