package com.example.core.user.trainer.application.service

import com.example.core.multimedia.application.port.`in`.MultimediaUploadUseCase
import com.example.core.user.application.port.command.UpdateProfileCommand
import com.example.core.user.application.service.AbstractUserService
import com.example.core.user.trainer.application.port.`in`.TrainerUseCase
import com.example.core.user.trainer.application.port.out.TrainerJpaPort
import org.springframework.stereotype.Service

@Service
class TrainerService(
    private val multimediaUploadUseCase: MultimediaUploadUseCase,
    private val trainerJpaPort: TrainerJpaPort,
) : TrainerUseCase, AbstractUserService(multimediaUploadUseCase) {
    override fun updateProfile(command: UpdateProfileCommand) {
        trainerJpaPort.updateProfile(command)
    }
}
