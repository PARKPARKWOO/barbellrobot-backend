package com.example.core.exercise.adapter.out.persistence.repository

import com.example.core.exercise.adapter.out.persistence.entity.relationship.ItemYoutubeInfo
import org.springframework.data.jpa.repository.JpaRepository

interface ItemYoutubeInfoRepository : JpaRepository<ItemYoutubeInfo, Long>
