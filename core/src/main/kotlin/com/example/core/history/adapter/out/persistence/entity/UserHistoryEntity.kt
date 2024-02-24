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
    var userId: UUID,
    @Column(name = "attendance")
    var attendance: Boolean = true,
    @Column(name = "breakfast_images_uri")
    @ElementCollection
    @CollectionTable(name = "user_history_breakfast_images", joinColumns = [JoinColumn(name = "user_history_id")])
    var breakfastImagesUri: MutableList<String> = mutableListOf(),
    @Column(name = "breakfast_foods")
    @ElementCollection
    @CollectionTable(name = "user_history_breakfast_foods", joinColumns = [JoinColumn(name = "user_history_id")])
    var breakfastFoods: MutableList<String> = mutableListOf(),
    @Column(name = "lunch_images_uri")
    @ElementCollection
    @CollectionTable(name = "user_history_lunch_images", joinColumns = [JoinColumn(name = "user_history_id")])
    var lunchImagesUri: MutableList<String> = mutableListOf(),
    @Column(name = "lunch_foods")
    @ElementCollection
    @CollectionTable(name = "user_history_lunch_foods", joinColumns = [JoinColumn(name = "user_history_id")])
    var lunchFoods: MutableList<String> = mutableListOf(),
    @Column(name = "breakfast_images_uri")
    @ElementCollection
    @CollectionTable(name = "user_history_dinner_images", joinColumns = [JoinColumn(name = "user_history_id")])
    var dinnerImagesUri: MutableList<String> = mutableListOf(),
    @Column(name = "dinner_foods")
    @ElementCollection
    @CollectionTable(name = "user_history_dinner_foods", joinColumns = [JoinColumn(name = "user_history_id")])
    var dinnerFoods: MutableList<String> = mutableListOf(),
    @Column(name = "today_images")
    @ElementCollection
    @CollectionTable(name = "user_history_today_images", joinColumns = [JoinColumn(name = "user_history_id")])
    var todayImages: MutableList<String> = mutableListOf(),
    @Column(name = "today_video")
    @ElementCollection
    @CollectionTable(name = "user_history_today_video", joinColumns = [JoinColumn(name = "user_history_id")])
    var todayVideo: MutableList<String> = mutableListOf(),
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
                images = breakfastImagesUri,
            ),
            lunch = Lunch(
                foods = lunchFoods,
                images = lunchImagesUri,
            ),
            dinner = Dinner(
                foods = dinnerFoods,
                images = dinnerImagesUri,
            ),
            createdAt = today,
            todayImages = todayImages,
            todayVideo = todayVideo,
            updatedAt = updatedAt,
        )
    }

    fun addBreakfastFoods(vararg foods: String) {
        this.breakfastFoods.addAll(foods)
    }

    fun addBreakfastImages(vararg images: String) {
        this.breakfastImagesUri.addAll(images)
    }

    fun addLunchFoods(vararg foods: String) {
        this.lunchFoods.addAll(foods)
    }

    fun addLunchImages(vararg images: String) {
        this.lunchImagesUri.addAll(images)
    }

    fun addDinnerFoods(vararg foods: String) {
        this.dinnerFoods.addAll(foods)
    }

    fun addDinnerImages(vararg images: String) {
        this.dinnerImagesUri.addAll(images)
    }

    fun addTodayImages(vararg images: String) {
        this.todayImages.addAll(images)
    }

    fun addTodayVideo(vararg video: String) {
        this.todayVideo.addAll(video)
    }
}
