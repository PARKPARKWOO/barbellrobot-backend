package com.example.core.history.application.port.out

import com.example.core.history.application.port.out.command.SaveExerciseHistoryCommand

interface ExerciseHistoryJpaPort {
    fun save(command: SaveExerciseHistoryCommand)

    fun saveAll(command: List<SaveExerciseHistoryCommand>)
}
