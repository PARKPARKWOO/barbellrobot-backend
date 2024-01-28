package com.example.core.user.member.adapter.out.persistence.repository

import com.example.core.user.member.adapter.out.persistence.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MemberRepository : JpaRepository<MemberEntity, UUID> {
    fun findByNickname(nickname: String): MemberEntity?

    fun findByEmailAndPassword(email: String, password: String): MemberEntity?
}
