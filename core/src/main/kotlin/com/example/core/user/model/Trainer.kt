package com.example.core.user.model

import com.example.core.user.model.interfaces.User
import com.example.core.common.constants.DomainConstants
import java.util.UUID

data class Trainer(
    val id: UUID,
    val email: String,
    val password: String,
    val role: Role,
) : User {
    override fun getClaims(): Map<String, Any> {
        val claims = mutableMapOf<String, Any>()
        claims[DomainConstants.USER_ID] = id
        claims[DomainConstants.USER_ROLE] = role
        return claims
    }
}

data class TrainerInfo(
    val nickname: String,
    val gender: Gender,
    val gymName: String,
    val street: String,
    val city: String,
    val country: String,
    val exerciseYears: Int,
    val introduce: String?,
)
