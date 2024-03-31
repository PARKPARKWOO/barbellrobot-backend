package com.example.core.user.application.service

import com.example.core.multimedia.application.port.`in`.MultimediaUploadUseCase
import com.example.core.user.application.port.command.UpdateProfileCommand
import com.example.core.user.application.port.command.UploadProfileCommand
import com.example.core.user.application.port.`in`.UserUseCase
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
abstract class AbstractUserService(
    private val multimediaUploadUseCase: MultimediaUploadUseCase,
) : UserUseCase {
    abstract fun updateProfile(command: UpdateProfileCommand)

    @Transactional
    override fun uploadProfile(command: UploadProfileCommand) {
        val imageUri = runBlocking {
            multimediaUploadUseCase.uploadMultipartFile(command.file)
        }
        imageUri?.let {
            val updateCommand = UpdateProfileCommand(
                userId = command.id,
                uri = it,
            )
            updateProfile(updateCommand)
        }
    }
}
