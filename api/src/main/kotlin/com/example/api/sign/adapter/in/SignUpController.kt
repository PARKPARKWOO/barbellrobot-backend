package com.example.api.sign.adapter.`in`

import com.example.api.sign.adapter.`in`.request.SignUpMemberWithEmailRequest
import com.example.api.sign.adapter.`in`.request.SignUpTrainerWithEmailRequest
import com.example.core.sign.application.port.`in`.SignUpMemberUseCase
import com.example.core.sign.application.port.`in`.SignUpTrainerUseCase
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/sign-up")
class SignUpController(
    private val signUpMemberUseCase: SignUpMemberUseCase,
    private val signUpTrainerUseCase: SignUpTrainerUseCase,
) {
    @PostMapping("/email/member")
    fun signUpMember(
        @RequestBody @Valid
        request: SignUpMemberWithEmailRequest,
    ) {
        signUpMemberUseCase.signUpWithEmail(request.toCommand())
    }

    @PostMapping("/email/trainer")
    fun signUpTrainer(
        @RequestBody @Valid
        request: SignUpTrainerWithEmailRequest,
    ) {
        signUpTrainerUseCase.signUpWithEmail(request.toCommand())
    }
}
