package com.example.api.member.`in`.reponse

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class SignUpResponse(
    @JsonProperty("member_id")
    val memberId: UUID,
)

data class AuthenticateEmailResponse(
    @JsonProperty("code")
    val code: Int,
)