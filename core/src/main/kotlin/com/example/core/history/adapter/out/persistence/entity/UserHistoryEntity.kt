package com.example.core.history.adapter.out.persistence.entity

import com.example.domain.history.Breakfast
import com.example.domain.history.Dinner
import com.example.domain.history.Lunch
import com.example.domain.history.UserHistory
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UuidGenerator
import org.hibernate.type.SqlTypes
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

const val USER_HISTORY_TABLE_NAME = "user_history"

@Entity
@Table(name = USER_HISTORY_TABLE_NAME)
@DynamicUpdate
class UserHistoryEntity(
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    var id: UUID = UUID.randomUUID(),
    @Column(name = "today")
    @CreatedDate
    @NotNull
    var today: LocalDate,
    @Column(name = "user_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    var userId: UUID,
    @Column(name = "attendance")
    var attendance: Boolean = true,
    @Column(name = "breakfast_images_uri")
    @ElementCollection
    @CollectionTable(name = "user_history_breakfast_image_id", joinColumns = [JoinColumn(name = "user_history_id")])
    var breakfastImageIds: MutableList<Long> = mutableListOf(),
    @Column(name = "breakfast_foods")
    @ElementCollection
    @CollectionTable(name = "user_history_breakfast_foods", joinColumns = [JoinColumn(name = "user_history_id")])
    var breakfastFoods: MutableList<String> = mutableListOf(),
    @Column(name = "lunch_images_uri")
    @ElementCollection
    @CollectionTable(name = "user_history_lunch_image_id", joinColumns = [JoinColumn(name = "user_history_id")])
    var lunchImageIds: MutableList<Long> = mutableListOf(),
    @Column(name = "lunch_foods")
    @ElementCollection
    @CollectionTable(name = "user_history_lunch_foods", joinColumns = [JoinColumn(name = "user_history_id")])
    var lunchFoods: MutableList<String> = mutableListOf(),
    @Column(name = "breakfast_images_uri")
    @ElementCollection
    @CollectionTable(name = "user_history_dinner_image_id", joinColumns = [JoinColumn(name = "user_history_id")])
    var dinnerImageIds: MutableList<Long> = mutableListOf(),
    @Column(name = "dinner_foods")
    @ElementCollection
    @CollectionTable(name = "user_history_dinner_foods", joinColumns = [JoinColumn(name = "user_history_id")])
    var dinnerFoods: MutableList<String> = mutableListOf(),
    @Column(name = "today_images")
    @ElementCollection
    @CollectionTable(name = "user_history_today_images", joinColumns = [JoinColumn(name = "user_history_id")])
    var todayImageIds: MutableList<Long> = mutableListOf(),
    @Column(name = "today_video")
    @ElementCollection
    @CollectionTable(name = "user_history_today_video", joinColumns = [JoinColumn(name = "user_history_id")])
    var todayVideo: MutableList<Long> = mutableListOf(),
) {
    @Column(name = "updated_at")
    @LastModifiedDate
    @NotNull
    lateinit var updatedAt: LocalDateTime
    fun toDomain(): UserHistory {
        return UserHistory(
            id = id,
            userId = userId,
            breakfast = Breakfast(
                foods = breakfastFoods,
                imageIds = breakfastImageIds,
            ),
            lunch = Lunch(
                foods = lunchFoods,
                imageIds = lunchImageIds,
            ),
            dinner = Dinner(
                foods = dinnerFoods,
                imageIds = dinnerImageIds,
            ),
            createdAt = today,
            todayVideo = todayVideo,
            updatedAt = updatedAt,
            todayImageIds = todayImageIds,
        )
    }

    fun addBreakfastFoods(foods: List<String>) {
        this.breakfastFoods.addAll(foods)
    }

    fun addBreakfastImages(images: List<Long>) {
        this.breakfastImageIds.addAll(images)
    }

    fun addLunchFoods(foods: List<String>) {
        this.lunchFoods.addAll(foods)
    }

    fun addLunchImages(images: List<Long>) {
        this.lunchImageIds.addAll(images)
    }

    fun addDinnerFoods(foods: List<String>) {
        this.dinnerFoods.addAll(foods)
    }

    fun addDinnerImages(images: List<Long>) {
        this.dinnerImageIds.addAll(images)
    }

    fun addTodayImages(images: List<Long>) {
        this.todayImageIds.addAll(images)
    }

    fun addTodayVideo(video: List<Long>) {
        this.todayVideo.addAll(video)
    }
}
