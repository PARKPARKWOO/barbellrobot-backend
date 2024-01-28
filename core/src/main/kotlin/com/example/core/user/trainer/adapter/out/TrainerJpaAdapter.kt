package com.example.core.user.trainer.adapter.out

import com.example.core.user.application.`in`.command.SignInWithEmailCommand
import com.example.core.user.trainer.adapter.out.persistence.entity.GymAddress
import com.example.core.user.trainer.adapter.out.persistence.entity.TrainerEntity
import com.example.core.user.trainer.adapter.out.persistence.repository.TrainerRepository
import com.example.core.user.trainer.application.out.TrainerJpaPort
import com.example.core.user.trainer.application.out.command.SignUpTrainerCommand
import com.example.domain.user.Role
import com.example.domain.user.Trainer
import org.springframework.stereotype.Component

@Component
class TrainerJpaAdapter(
    private val trainerRepository: TrainerRepository,
): TrainerJpaPort {
    override fun signUpTrainer(command: SignUpTrainerCommand) {
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
        )
        trainerRepository.save(trainerEntity)
    }

    override fun signInWithEmail(command: SignInWithEmailCommand): Trainer? {
        return trainerRepository.findByEmailAndPassword(
            email = command.email,
            password = command.password,
        )?.toDomain()
    }
}