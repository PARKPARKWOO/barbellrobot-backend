package com.example.application.user

import com.example.application.common.transaction.Tx
import com.example.core.multimedia.port.`in`.MultimediaUploadUseCase
import com.example.core.user.port.command.UpdateProfileCommand
import com.example.core.user.port.command.UploadProfileCommand
import com.example.core.user.port.`in`.UserUseCase
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
abstract class AbstractUserService(
    private val multimediaUploadUseCase: MultimediaUploadUseCase,
) : UserUseCase {
    abstract fun updateProfile(command: UpdateProfileCommand)

    override fun uploadProfile(command: UploadProfileCommand) {
        val imageUri = runBlocking {
            multimediaUploadUseCase.uploadMultipartFile(command.toMultimediaDto())
        }
        imageUri?.let {
            val updateCommand = UpdateProfileCommand(
                userId = command.id,
                uri = it,
            )
            Tx.writeTx { updateProfile(updateCommand) }
        }
    }
}
