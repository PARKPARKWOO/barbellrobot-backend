package com.example.core.mail.adapter.out

import com.example.common.generate.GenerateRandom
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.redis.RedisDriver
import com.example.core.mail.application.port.out.EmailVerifyPort
import com.example.core.mail.application.port.out.command.VerifyAuthenticationSuccessCommand
import com.example.core.sign.application.port.`in`.command.VerifyEmailCommand
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class EmailVerifyAdapter(
    private val redisDriver: RedisDriver,
) : EmailVerifyPort {
    override fun verifyEmail(command: VerifyEmailCommand): UUID {
        redisDriver.getValue(getVerifyKey(command.email), String::class.java)?.let { authenticationNumber ->
            if (authenticationNumber.toInt() != command.authenticationNumber) {
                throw ServiceException(ErrorCode.AUTHENTICATION_NUMBER_FAIL)
            }
        } ?: throw ServiceException(ErrorCode.AUTHENTICATION_NUMBER_FAIL)
        val authenticationSuccess = GenerateRandom.generateUUID()
        val key = getSuccessKey(command.email)
        redisDriver.setValue(key, authenticationSuccess.toString(), SUCCESS_AUTHENTICATION_TTL)
        return authenticationSuccess
    }

    override fun saveAuthenticationNumber(email: String): Int {
        val authenticationNumber = GenerateRandom.generateNumber(
            GENERATE_AUTHENTICATION_NUMBER_FROM,
            GENERATE_AUTHENTICATION_NUMBER_UNTIL,
        )
        redisDriver.setValue(getVerifyKey(email), authenticationNumber.toString(), EMAIL_VERIFY_TTL)
        return authenticationNumber
    }

    override fun verifyAuthenticationSuccess(command: VerifyAuthenticationSuccessCommand) {
        val key = getSuccessKey(command.email)
        redisDriver.getValue(key, String::class.java) ?: ServiceException(ErrorCode.AUTHENTICATION_NUMBER_UN_RESOLVE)
    }

    private fun getVerifyKey(email: String) = EMAIL_VERIFY_KEY_PREFIX + email

    private fun getSuccessKey(email: String) = SUCCESS_AUTHENTICATION_KEY + email
}

const val EMAIL_VERIFY_TTL = 180L // 3분 60 * 3
const val EMAIL_VERIFY_KEY_PREFIX = "email_verify"
const val GENERATE_AUTHENTICATION_NUMBER_FROM = 100000
const val GENERATE_AUTHENTICATION_NUMBER_UNTIL = 999999
const val SUCCESS_AUTHENTICATION_KEY = "Authentication"
const val SUCCESS_AUTHENTICATION_TTL = 900L // 15분 60 * 15
