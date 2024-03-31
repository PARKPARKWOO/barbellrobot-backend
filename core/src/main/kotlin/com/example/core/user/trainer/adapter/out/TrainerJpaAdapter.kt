package com.example.core.user.trainer.adapter.out

import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.user.application.port.command.UpdateProfileCommand
import com.example.core.user.trainer.adapter.out.persistence.entity.GymAddress
import com.example.core.user.trainer.adapter.out.persistence.entity.TrainerEntity
import com.example.core.user.trainer.adapter.out.persistence.repository.TrainerRepository
import com.example.core.user.trainer.application.port.command.SignUpTrainerCommand
import com.example.core.user.trainer.application.port.out.TrainerJpaPort
import com.example.domain.user.Role
import com.example.domain.user.Trainer
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class TrainerJpaAdapter(
    private val trainerRepository: TrainerRepository,
) : TrainerJpaPort {
    override fun signUpTrainer(command: SignUpTrainerCommand): Trainer {
        val trainerEntity = TrainerEntity(
            nickname = command.nickname,
            email = command.email,
            password = command.password,
            gymName = command.gymName,
            gymAddress = GymAddress(
                street = command.street,
                city = command.city,
                country = command.country,
            ),
            exerciseYears = command.exerciseYears,
            gender = command.gender,
            introduce = command.introduce,
            role = Role.ROLE_TRAINER,
            profile = null,
        )
        return trainerRepository.save(trainerEntity).toDomain()
    }

    override fun signInWithEmail(command: SignInWithEmailCommand): Trainer? {
        return trainerRepository.findByEmailAndPassword(
            email = command.email,
            password = command.password,
        )?.toDomain()
    }

    override fun updateProfile(command: UpdateProfileCommand) {
        getEntity(command.userId).uploadProfile(command.uri)
    }

    private fun getEntity(trainerId: UUID): TrainerEntity {
        return trainerRepository.findById(trainerId).orElseThrow {
            throw ServiceException(ErrorCode.TRAINER_NOT_FOUND)
        }
    }
}
