package com.example.infrastructure.persistence.repository.exercise

import com.example.infrastructure.persistence.entity.exercise.ItemYoutubeInfo
import org.springframework.data.jpa.repository.JpaRepository

interface ItemYoutubeInfoRepository : JpaRepository<ItemYoutubeInfo, Long>
