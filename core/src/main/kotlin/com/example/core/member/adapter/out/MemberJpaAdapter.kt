package com.example.core.member.adapter.out

import com.example.core.member.adapter.out.persistence.repository.MemberRepository
import com.example.core.member.application.out.SignUpPort
import org.springframework.stereotype.Component

@Component
class MemberJpaAdapter(
    private val memberRepository: MemberRepository,
) : SignUpPort {
    override fun signMemberFromEmail() {

    }
}