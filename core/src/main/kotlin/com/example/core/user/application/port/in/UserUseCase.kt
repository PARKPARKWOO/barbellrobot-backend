package com.example.core.user.application.port.`in`

import com.example.core.user.application.port.command.UploadProfileCommand

interface UserUseCase {
    fun uploadProfile(command: UploadProfileCommand)
}
