package com.example.core.exercise.application.service

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.util.Tx
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseAreaEntity
import com.example.core.exercise.adapter.out.persistence.entity.ExerciseGoalEntity
import com.example.core.exercise.application.dto.QueryItemDto
import com.example.core.exercise.application.port.command.AddItemAreaRelationCommand
import com.example.core.exercise.application.port.command.AddItemGoalRelationCommand
import com.example.core.exercise.application.port.command.AddItemYoutubeCommand
import com.example.core.exercise.application.port.command.SaveExerciseItemCommand
import com.example.core.exercise.application.port.`in`.ExerciseItemUseCase
import com.example.core.exercise.application.port.out.ExerciseAreaJpaPort
import com.example.core.exercise.application.port.out.ExerciseGoalJpaPort
import com.example.core.exercise.application.port.out.ExerciseItemJpaPort
import com.example.core.exercise.application.port.out.ItemAreaRelationshipJpaPort
import com.example.core.exercise.application.port.out.ItemGoalRelationshipJpaPort
import com.example.core.exercise.application.port.out.ItemYoutubeJpaPort
import com.example.core.multimedia.application.port.`in`.MultimediaUploadUseCase
import com.example.domain.exercise.ExerciseItem
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExerciseItemService(
    private val exerciseItemJpaPort: ExerciseItemJpaPort,
    private val exerciseGoalJpaPort: ExerciseGoalJpaPort,
    private val exerciseAreaJpaPort: ExerciseAreaJpaPort,
    private val itemGoalRelationshipJpaPort: ItemGoalRelationshipJpaPort,
    private val itemAreaRelationshipJpaPort: ItemAreaRelationshipJpaPort,
    private val multimediaUploadUseCase: MultimediaUploadUseCase,
    private val itemYoutubeJpaPort: ItemYoutubeJpaPort,
) : ExerciseItemUseCase {
    override suspend fun saveExerciseItem(command: SaveExerciseItemCommand) {
        val (area, goal) = coroutineScope {
            val goalJob = async {
                getExerciseGoals(command.exerciseGoals)
                    ?: throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_GOAL)
            }

            val areaJob = async {
                getExerciseAreas(command.exerciseAreas)
                    ?: throw ServiceException(ErrorCode.NOT_FOUND_EXERCISE_AREA)
            }
            awaitAll(areaJob, goalJob)
        }
        val imageUrls = command.image?.let {
            multimediaUploadUseCase.uploadMultipartFiles(it)
        } ?: emptyList()

        val videoUrls = command.video?.let {
            multimediaUploadUseCase.uploadMultipartFiles(it)
        } ?: emptyList()
        val goals = goal.map { it as ExerciseGoalEntity }
        val areas = area.map { it as ExerciseAreaEntity }
        val itemId = exerciseItemJpaPort.saveExerciseItem(
            command.toOutCommand(
                exerciseGoals = goals.toMutableList(),
                exerciseAreas = areas.toMutableList(),
                video = videoUrls.toMutableList(),
                image = imageUrls.toMutableList(),
            ),
        )
        saveItemRelationship(itemId, goals.map { it.id }, areas.map { it.id })
    }

    private suspend fun saveItemRelationship(
        itemId: Long,
        goalIds: List<Long>,
        areaIds: List<Long>,
    ) = coroutineScope {
        val addItemGoalRelationCommand = AddItemGoalRelationCommand(
            itemId = itemId,
            goalIds = goalIds,
        )
        val itemGoalJob = async {
            saveItemGoalRelationship(addItemGoalRelationCommand)
        }
        val addItemAreaRelationCommand = AddItemAreaRelationCommand(
            itemId = itemId,
            areaIds = areaIds,
        )
        val itemAreaJob = async {
            saveItemAreaRelationship(addItemAreaRelationCommand)
        }
        awaitAll(itemAreaJob, itemGoalJob)
    }

    private fun getExerciseGoals(ids: List<Long>) = Tx.readTx {
        exerciseGoalJpaPort.getExerciseGoals(ids)
    }

    private fun getExerciseAreas(ids: List<Long>) = Tx.readTx {
        exerciseAreaJpaPort.getExerciseAreas(ids)
    }

    private fun saveItemAreaRelationship(command: AddItemAreaRelationCommand) = Tx.writeTx {
        itemAreaRelationshipJpaPort.addRelationship(command)
    }

    private fun saveItemGoalRelationship(command: AddItemGoalRelationCommand) = Tx.writeTx {
        itemGoalRelationshipJpaPort.addRelationship(command)
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

    @Transactional(readOnly = true)
    override fun findAll(): List<ExerciseItem> {
        return exerciseItemJpaPort.findAll()
    }

    @Transactional(readOnly = true)
    override fun findAllItemsQuery(ids: List<Long>?): List<QueryItemDto> {
        return ids?.let {
            exerciseItemJpaPort.findInIds(it)
        } ?: exerciseItemJpaPort.findAllItemsQuery()
    }

    @Transactional
    override fun addYoutubeLink(command: AddItemYoutubeCommand) {
        itemYoutubeJpaPort.add(command)
    }

    @Transactional
    override fun deleteYoutubeLink(id: Long) {
        itemYoutubeJpaPort.delete(id)
    }
}
