package com.example.api.member.`in`

import com.example.api.member.`in`.request.AuthenticateEmailRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/member")
class MemberController(

) {
    @PostMapping("/v1/email-verify")
    fun authenticateEmail(
        @RequestBody request: AuthenticateEmailRequest,
    ) {

    }

    @PostMapping("/v1/sign-up")
    fun signUp() {
    }
}
