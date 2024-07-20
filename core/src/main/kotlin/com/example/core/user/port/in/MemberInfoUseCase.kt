package com.example.core.user.port.`in`

import com.example.core.user.dto.MemberDetailQueryDto
import com.example.core.user.model.MemberInfo
import com.example.core.user.port.command.SaveMemberInfoCommand
import com.example.core.user.port.command.UpdateMemberInfoCommand
import com.example.core.user.port.command.UpdateNicknameCommand
import java.util.UUID

interface MemberInfoUseCase {
    fun update(command: UpdateMemberInfoCommand)

    fun getInfo(memberId: UUID): MemberInfo?

    fun updateNickname(command: UpdateNicknameCommand)

    fun save(command: SaveMemberInfoCommand)

    fun getDetail(memberId: UUID): MemberDetailQueryDto
}
