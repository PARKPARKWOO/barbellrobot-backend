package com.example.application.history.service

import com.example.application.common.util.DateTimeConvert.getEndOfMonth
import com.example.application.common.util.DateTimeConvert.getEndOfWeek
import com.example.application.common.util.DateTimeConvert.getStartOfMonth
import com.example.application.common.util.DateTimeConvert.getStartOfWeek
import com.example.core.history.port.command.AddDietJpaCommand
import com.example.core.history.port.command.ExerciseTodayCommand
import com.example.core.history.port.`in`.GenerateHistoryUseCase
import com.example.core.history.port.`in`.HistoryQueryUseCase
import com.example.core.history.port.out.ExerciseHistoryJpaPort
import com.example.core.history.port.out.UserHistoryJpaPort
import com.example.core.history.port.out.command.AttendanceTodayCommand
import com.example.core.history.port.out.command.SaveExerciseHistoryCommand
import com.example.core.history.port.out.query.FindUserHistoryQuery
import com.example.core.history.port.query.GetHistoryQuery
import com.example.core.history.dto.HistoryResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class HistoryService(
    private val userHistoryJpaPort: UserHistoryJpaPort,
    private val exerciseHistoryJpaPort: ExerciseHistoryJpaPort,
) : GenerateHistoryUseCase, HistoryQueryUseCase {
    @Transactional
    override fun exerciseToday(command: ExerciseTodayCommand) {
        exerciseHistoryJpaPort.saveAll(
            command.items.map {
                SaveExerciseHistoryCommand(
                    itemId = it.itemId,
                    weight = it.weight,
                    exerciseSet = it.exerciseSet,
                    userHistoryId = command.userHistoryId,
                    count = it.count,
                )
            },
        )
    }

    @Transactional
    override fun attendanceToday(userId: UUID): UUID {
        val attendanceTodayCommand = AttendanceTodayCommand(
            userId = userId,
            today = LocalDate.now(),
        )
        return userHistoryJpaPort.findUserHistory(attendanceTodayCommand)?.id
            ?: run {
                userHistoryJpaPort.attendanceToday(attendanceTodayCommand)
            }
    }

    @Transactional
    override fun addDietCommand(command: AddDietJpaCommand) {
        command.images?.let {
            userHistoryJpaPort.addImage(command.toImageCommand())
        }
        command.foods?.let {
            userHistoryJpaPort.addFood(command.toFoodCommand())
        }
    }

    @Transactional(readOnly = true)
    override fun getHistoryFromWeek(query: GetHistoryQuery): List<HistoryResponseDto> {
        val historyQuery = FindUserHistoryQuery(
            userId = query.userId,
            startDate = getStartOfWeek(query.localDate),
            endDate = getEndOfWeek(query.localDate),
        )
        return userHistoryJpaPort.queryUserHistory(historyQuery) ?: emptyList()
    }

    @Transactional(readOnly = true)
    override fun getHistoryFromMonth(query: GetHistoryQuery): List<HistoryResponseDto> {
        val today = query.localDate
        val jpaQuery = FindUserHistoryQuery(
            userId = query.userId,
            startDate = getStartOfMonth(today),
            endDate = getEndOfMonth(today),
        )
        return userHistoryJpaPort.queryUserHistory(jpaQuery) ?: emptyList()
    }

    @Transactional(readOnly = true)
    override fun getHistoryFromToday(userId: UUID): HistoryResponseDto? {
        val today = LocalDate.now()
        val query = FindUserHistoryQuery(
            userId = userId,
            startDate = today,
            endDate = today,
        )
        return userHistoryJpaPort.queryUserHistory(query)?.get(0)
    }
}
