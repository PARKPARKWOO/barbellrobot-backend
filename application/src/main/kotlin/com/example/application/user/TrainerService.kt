package com.example.application.user

import com.example.core.multimedia.port.`in`.MultimediaUploadUseCase
import com.example.core.user.port.command.UpdateProfileCommand
import com.example.core.user.port.`in`.TrainerUseCase
import com.example.core.user.port.out.TrainerJpaPort
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
