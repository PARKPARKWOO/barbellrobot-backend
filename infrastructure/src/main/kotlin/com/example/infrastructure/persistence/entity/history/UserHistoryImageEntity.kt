package com.example.infrastructure.persistence.entity.history

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class UserHistoryImageEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_history_id")
    val userHistoryEntity: UserHistoryEntity,
    @Column(name = "image_url")
    var imageUrl: String,
)
