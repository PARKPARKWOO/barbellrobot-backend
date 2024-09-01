package com.example.core.history.port.out

import com.example.core.history.port.out.command.SaveExerciseHistoryCommand

interface ExerciseHistoryJpaPort {
    fun save(command: SaveExerciseHistoryCommand)

    fun saveAll(command: List<SaveExerciseHistoryCommand>)
}
