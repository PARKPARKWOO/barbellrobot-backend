package com.example.infrastructure.persistence.repository.exercise

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemYoutubeInfo
import org.springframework.data.jpa.repository.JpaRepository

interface ItemYoutubeInfoRepository : JpaRepository<ItemYoutubeInfo, Long>
