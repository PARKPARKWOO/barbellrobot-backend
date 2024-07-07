package com.example.core.user.member.application.out

import com.example.core.user.member.application.command.SaveMemberInfoCommand
import com.example.core.user.member.application.command.UpdateNicknameCommand
import com.example.core.user.member.dto.MemberDetailQueryDto
import com.example.core.user.model.MemberInfo
import java.util.UUID

interface MemberInfoJpaPort {
    fun update(memberInfo: MemberInfo)

    fun getInfo(memberId: UUID): com.example.core.user.member.adapter.out.persistence.entity.MemberInfo?

    fun updateNickname(command: UpdateNicknameCommand)

    fun save(command: SaveMemberInfoCommand)

    fun getDetail(memberId: UUID): MemberDetailQueryDto?
}
