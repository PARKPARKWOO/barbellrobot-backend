package com.example.core.member.application.out

import com.example.domain.member.Member
import java.util.UUID

interface MemberPort {
    fun getMember(id: UUID): Member

    fun getMember(nickname: String): Member?
}
