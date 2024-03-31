package com.example.core.user.member.adapter.out.persistence.repository

import com.example.core.user.member.adapter.out.persistence.entity.MemberGoalEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MemberGoalRepository : JpaRepository<MemberGoalEntity, UUID>
