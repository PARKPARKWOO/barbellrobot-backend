package com.example.core.sign.application.service

import com.example.common.jwt.JwtResponseDto
import com.example.common.jwt.JwtTokenService
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.sign.application.port.`in`.SignInTrainerUseCase
import com.example.core.sign.application.port.`in`.command.SignInWithEmailCommand
import com.example.core.user.trainer.application.port.out.TrainerJpaPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignInTrainerService(
    private val trainerJpaPort: TrainerJpaPort,
    private val jwtTokenService: JwtTokenService,
) : SignInTrainerUseCase, AbstractSignInService() {
    @Transactional(readOnly = true)
    override fun signInWithEmail(command: SignInWithEmailCommand): JwtResponseDto {
        return trainerJpaPort.signInWithEmail(command)?.let { member ->
            val claims = member.getClaims()
            jwtTokenService.build(claims)
        } ?: throw ServiceException(ErrorCode.MEMBER_NOT_FOUND)
    }
}
