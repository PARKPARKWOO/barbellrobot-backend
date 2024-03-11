package com.example.core.history.adapter.out

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.history.adapter.out.persistence.entity.UserHistoryEntity
import com.example.core.history.adapter.out.persistence.repository.UserHistoryRepository
import com.example.core.history.application.port.out.AttendanceTodayCommand
import com.example.core.history.application.port.out.UserHistoryJpaPort
import com.example.core.history.application.port.out.command.AddDietFoodCommand
import com.example.core.history.application.port.out.command.AddDietImageCommand
import com.example.core.history.application.port.out.query.FindUserHistoryQuery
import com.example.core.history.dto.HistoryResponseDto
import com.example.domain.history.Diet
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserHistoryJpaAdapter(
    private val userHistoryRepository: UserHistoryRepository,
) : UserHistoryJpaPort {
    override fun attendanceToday(command: AttendanceTodayCommand): UUID {
        val userHistoryEntity = UserHistoryEntity(
            userId = command.userId,
            today = command.today,
        )
        return userHistoryRepository.save(userHistoryEntity).id
    }

    override fun addImage(command: AddDietImageCommand) {
        val entity = getEntity(command.todayHistoryId)
        when (command.type) {
            Diet.BREAKFAST -> entity.addBreakfastImages(command.imageIds)
            Diet.LUNCH -> entity.addLunchImages(command.imageIds)
            Diet.DINNER -> entity.addDinnerImages(command.imageIds)
        }
    }

    override fun addFood(command: AddDietFoodCommand) {
        val entity = getEntity(command.todayHistoryId)
        when (command.type) {
            Diet.BREAKFAST -> entity.addBreakfastFoods(command.foods)
            Diet.LUNCH -> entity.addLunchFoods(command.foods)
            Diet.DINNER -> entity.addDinnerFoods(command.foods)
        }
    }

    override fun queryUserHistory(query: FindUserHistoryQuery): List<HistoryResponseDto>? {
        return userHistoryRepository.getHistoryBetween(
            userId = query.userId,
            startDate = query.startDate,
            endDate = query.endDate,
        )
    }

    private fun getEntity(historyId: UUID) =
        userHistoryRepository.findById(historyId).orElseThrow {
            throw ServiceException(ErrorCode.NOT_FOUND_USER_HISTORY)
        }
}
