package com.example.core.image.adapter.out.persistence.repository

import com.example.core.image.adapter.out.persistence.entity.ImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ImageRepository : JpaRepository<ImageEntity, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from ImageEntity i where i.id in :ids")
    fun deleteQuery(@Param("ids") ids: List<Long>)
}
