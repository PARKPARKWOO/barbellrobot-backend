package com.example.core.user.member.application.`in`

import com.example.core.user.member.application.command.SaveMemberInfoCommand
import com.example.core.user.member.application.command.UpdateMemberInfoCommand
import com.example.core.user.member.application.command.UpdateNicknameCommand
import com.example.core.user.member.dto.MemberDetailQueryDto
import java.util.UUID

interface MemberInfoUseCase {
    fun update(command: UpdateMemberInfoCommand)

    fun getInfo(memberId: UUID): com.example.core.user.member.adapter.out.persistence.entity.MemberInfo?

    fun updateNickname(command: UpdateNicknameCommand)

    fun save(command: SaveMemberInfoCommand)

    fun getDetail(memberId: UUID): MemberDetailQueryDto
}
