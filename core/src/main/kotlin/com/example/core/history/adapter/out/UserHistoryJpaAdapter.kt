package com.example.core.history.adapter.out

import com.example.core.history.adapter.out.persistence.entity.UserHistoryEntity
import com.example.core.history.adapter.out.persistence.repository.UserHistoryRepository
import com.example.core.history.application.port.out.AttendanceTodayCommand
import com.example.core.history.application.port.out.UserHistoryJpaPort
import org.springframework.stereotype.Component

@Component
class UserHistoryJpaAdapter(
    private val userHistoryRepository: UserHistoryRepository,
) : UserHistoryJpaPort {
    override fun attendanceToday(command: AttendanceTodayCommand) {
        val userHistoryEntity = UserHistoryEntity(
            userId = command.userId,
            today = command.today,
        )
        userHistoryRepository.save(userHistoryEntity)
    }
}
