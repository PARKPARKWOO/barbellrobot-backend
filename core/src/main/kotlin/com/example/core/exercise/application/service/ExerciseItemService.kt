package com.example.core.exercise.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.exercise.application.dto.QueryItemDto
import com.example.core.exercise.application.`in`.ExerciseItemUseCase
import com.example.core.exercise.application.`in`.command.SaveExerciseItemCommand
import com.example.core.exercise.application.out.ExerciseAreaJpaPort
import com.example.core.exercise.application.out.ExerciseGoalJpaPort
import com.example.core.exercise.application.out.ExerciseItemJpaPort
import com.example.core.exercise.application.out.ItemAreaRelationshipJpaPort
import com.example.core.exercise.application.out.ItemGoalRelationshipJpaPort
import com.example.core.exercise.application.out.command.AddItemAreaRelationCommand
import com.example.core.exercise.application.out.command.AddItemGoalRelationCommand
import com.example.domain.exercise.ExerciseItem
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseItemService(
    private val exerciseItemJpaPort: ExerciseItemJpaPort,
    private val exerciseGoalJpaPort: ExerciseGoalJpaPort,
    private val exerciseAreaJpaPort: ExerciseAreaJpaPort,
    private val itemGoalRelationshipJpaPort: ItemGoalRelationshipJpaPort,
    private val itemAreaRelationshipJpaPort: ItemAreaRelationshipJpaPort,
) : ExerciseItemUseCase {
    @Transactional
    override fun saveExerciseItem(command: SaveExerciseItemCommand) {
        val exerciseGoals = exerciseGoalJpaPort.getExerciseGoals(command.exerciseGoals)
            ?: throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_GOAL)
        val exerciseAreas = exerciseAreaJpaPort.getExerciseAreas(command.exerciseAreas)
            ?: throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_AREA)
        if (exerciseAreas.size != command.exerciseAreas.size) {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_AREA)
        }
        if (exerciseGoals.size != command.exerciseGoals.size) {
            throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_GOAL)
        }
        val itemId = exerciseItemJpaPort.saveExerciseItem(
            command.toOutCommand(
                exerciseGoals = exerciseGoals.toMutableList(),
                exerciseAreas = exerciseAreas.toMutableList(),
            ),
        )
        val addItemGoalRelationCommand = AddItemGoalRelationCommand(
            itemId = itemId,
            goalIds = command.exerciseGoals,
        )
        itemGoalRelationshipJpaPort.addRelationship(addItemGoalRelationCommand)

        val addItemAreaRelationCommand = AddItemAreaRelationCommand(
            itemId = itemId,
            areaIds = command.exerciseAreas,
        )
        itemAreaRelationshipJpaPort.addRelationship(addItemAreaRelationCommand)
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): ExerciseItem {
        return exerciseItemJpaPort.findById(id)
    }

    @Transactional(readOnly = true)
    override fun queryItem(id: Long): QueryItemDto {
        return exerciseItemJpaPort.queryItem(id) ?: throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_ITEM)
    }

    @Transactional
    override fun deleteItem(id: Long) {
        exerciseItemJpaPort.delete(id)
        itemAreaRelationshipJpaPort.deleteItemId(id)
        itemGoalRelationshipJpaPort.deleteByItemId(id)
    }
}
