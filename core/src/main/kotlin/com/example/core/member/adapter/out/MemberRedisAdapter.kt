package com.example.core.member.adapter.out

import com.example.common.generate.GenerateRandom
import com.example.core.common.error.ErrorCode
import com.example.core.common.error.ServiceException
import com.example.core.common.redis.RedisDriver
import com.example.core.member.application.`in`.command.SendVerifyEmailCommand
import com.example.core.member.application.`in`.command.VerifyEmailCommand
import com.example.core.member.application.out.EmailVerifyPort
import org.springframework.stereotype.Component

@Component
class MemberRedisAdapter(
    private val redisDriver: RedisDriver,
) : EmailVerifyPort {
    override fun verifyEmail(command: VerifyEmailCommand) {
        redisDriver.getValue(getVerifyKey(command.email))?.let { authenticationNumber ->
            if (authenticationNumber.toInt() != command.authenticationNumber) {
                throw ServiceException(ErrorCode.AUTHENTICATION_NUMBER_FAIL)
            }
        } ?: throw ServiceException(ErrorCode.AUTHENTICATION_NUMBER_FAIL)
    }

    override fun sendVerifyEmail(command: SendVerifyEmailCommand) {
        val authenticationNumber = GenerateRandom.generateNumber(
            GENERATE_AUTHENTICATION_NUMBER_FROM,
            GENERATE_AUTHENTICATION_NUMBER_UNTIL,
        )
        redisDriver.setValue(getVerifyKey(command.email), authenticationNumber.toString(), EMAIL_VERIFY_TTL)
    }

    private fun getVerifyKey(email: String) = EMAIL_VERIFY_KEY_PREFIX + email
}

const val EMAIL_VERIFY_TTL = 180L // 3분 60 * 3
const val EMAIL_VERIFY_KEY_PREFIX = "email_verify"
const val GENERATE_AUTHENTICATION_NUMBER_FROM = 100000
const val GENERATE_AUTHENTICATION_NUMBER_UNTIL = 999999
