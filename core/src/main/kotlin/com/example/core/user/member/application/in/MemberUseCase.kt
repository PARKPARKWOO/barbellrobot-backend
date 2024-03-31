package com.example.core.user.member.application.`in`

import com.example.core.user.application.port.`in`.UserUseCase
import com.example.core.user.member.application.command.UpdateMemberInfoCommand

interface MemberUseCase : UserUseCase {
    fun update(command: UpdateMemberInfoCommand)
}
