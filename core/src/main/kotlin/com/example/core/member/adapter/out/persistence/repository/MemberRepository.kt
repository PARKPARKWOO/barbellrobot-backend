package com.example.core.member.adapter.out.persistence.repository

import com.example.core.member.adapter.out.persistence.entity.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MemberRepository : JpaRepository<MemberEntity, UUID>
