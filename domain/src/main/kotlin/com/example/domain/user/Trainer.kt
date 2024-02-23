package com.example.domain.user

import java.util.UUID

data class Trainer(
    val id: UUID,
    val nickname: String,
    val email: String,
    val password: String,
    val role: Role,
    val gender: Gender,
    val gymName: String,
    val street: String,
    val city: String,
    val country: String,
    val exerciseYears: Int,
    val introduce: String,
) : User {
    override fun getClaims(): Map<String, Any> {
        val claims = mutableMapOf<String, Any>()
        claims["id"] = id
        claims["role"] = role
        return claims
    }
}
