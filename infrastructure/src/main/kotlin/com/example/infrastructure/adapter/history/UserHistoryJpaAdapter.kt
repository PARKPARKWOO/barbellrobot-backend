package com.example.infrastructure.adapter.history

import com.example.core.history.adapter.out.persistence.entity.DietFoodEntity
import com.example.core.history.adapter.out.persistence.entity.DietImageEntity
import com.example.core.history.adapter.out.persistence.entity.UserHistoryEntity
import com.example.core.history.adapter.out.persistence.repository.DietFoodRepository
import com.example.core.history.adapter.out.persistence.repository.DietImageRepository
import com.example.core.history.adapter.out.persistence.repository.UserHistoryRepository
import com.example.core.history.port.out.UserHistoryJpaPort
import com.example.core.history.port.out.command.AddDietFoodCommand
import com.example.core.history.port.out.command.AddDietImageCommand
import com.example.core.history.port.out.command.AttendanceTodayCommand
import com.example.core.history.port.out.query.FindUserHistoryQuery
import com.example.core.history.dto.HistoryResponseDto
import com.example.core.history.model.UserHistory
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserHistoryJpaAdapter(
    private val userHistoryRepository: UserHistoryRepository,
    private val dietFoodRepository: DietFoodRepository,
    private val dietImageRepository: DietImageRepository,
) : UserHistoryJpaPort {
    override fun findUserHistory(query: AttendanceTodayCommand): UserHistory? {
        return userHistoryRepository.findUserHistoryToday(
            userId = query.userId,
            today = query.today,
        )?.toDomain()
    }

    override fun attendanceToday(command: AttendanceTodayCommand): UUID {
        val userHistoryEntity = UserHistoryEntity(
            userId = command.userId,
            today = command.today,
        )
        return userHistoryRepository.save(userHistoryEntity).id
    }

    override fun addImage(command: AddDietImageCommand) {
        val imageEntity = command.imageIds.map {
            DietImageEntity(
                type = command.type,
                historyId = command.todayHistoryId,
                image = it,
            )
        }
        dietImageRepository.saveAll(imageEntity)
    }

    override fun addFood(command: AddDietFoodCommand) {
        val foodEntity = command.foods.map {
            DietFoodEntity(
                type = command.type,
                historyId = command.todayHistoryId,
                food = it,
            )
        }
        dietFoodRepository.saveAll(foodEntity)
    }

    override fun queryUserHistory(query: FindUserHistoryQuery): List<HistoryResponseDto>? {
        return userHistoryRepository.getHistoryBetween(
            userId = query.userId,
            startDate = query.startDate,
            endDate = query.endDate,
        )
    }
}
