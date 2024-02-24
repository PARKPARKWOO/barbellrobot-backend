package com.example.core.history.adapter.out

import com.example.core.history.adapter.out.persistence.repository.ExerciseHistoryRepository
import com.example.core.history.application.port.out.ExerciseHistoryJpaPort
import org.springframework.stereotype.Component

@Component
class ExerciseHistoryJpaAdapter(
    private val exerciseHistoryRepository: ExerciseHistoryRepository,
) : ExerciseHistoryJpaPort {

}
