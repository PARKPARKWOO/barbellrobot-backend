package com.example.core.user.port.`in`

import com.example.core.user.port.command.UploadProfileCommand

interface UserUseCase {
    fun uploadProfile(command: UploadProfileCommand)
}
