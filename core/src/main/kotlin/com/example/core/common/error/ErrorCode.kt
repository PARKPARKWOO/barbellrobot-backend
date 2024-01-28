package com.example.core.common.error

enum class ErrorCode(
    val message: String,
) {
    AUTHENTICATION_NUMBER_EXPIRED("인증번호 시간 만료 되었습니다."),
    AUTHENTICATION_NUMBER_FAIL("인증번호 가 잘못 되었습니다."),
    AUTHENTICATION_NUMBER_UN_RESOLVE("인증을 다시해주세요"),
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다."),
    DUPLICATE_NICKNAME("nickname 이 중복됩니다."),

    // trainer
    TRAINER_NOT_FOUND("trainer 를 찾을 수 없습니다."),
}
