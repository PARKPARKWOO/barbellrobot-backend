package com.example.core.multimedia.adapter.out.persistence.repository

import com.example.core.multimedia.adapter.out.persistence.entity.MultimediaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MultimediaRepository : JpaRepository<MultimediaEntity, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from MultimediaEntity i where i.id in :ids")
    fun deleteQuery(@Param("ids") ids: List<Long>)
}
