package com.example.core.common.error

enum class ErrorCode(
    val message: String,
) {
    // authentication email
    AUTHENTICATION_NUMBER_EXPIRED("인증번호 시간 만료 되었습니다."),
    AUTHENTICATION_NUMBER_FAIL("인증번호 가 잘못 되었습니다."),
    AUTHENTICATION_NUMBER_UN_RESOLVE("인증을 다시해주세요"),

    /**
     * thorw 시 던지는 message 사용
     * 여기선 Code 만 정의
     */
    NO_BEARER_TOKEN(""),
    EXPIRED_JWT(""),
    PARSE_JWT_FAILED(""),

    // resolver error
    AUTHENTICATION_RESOLVER_ERROR("authentication resolver error 에서 발생 하였습니다."),
    NOT_FOUND_REQUEST("HttpServlet 이 null 입니다."),

    // user
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다."),
    DUPLICATE_NICKNAME("nickname 이 중복됩니다."),

    // trainer
    TRAINER_NOT_FOUND("trainer 를 찾을 수 없습니다."),

    // exercise
    NOT_FOUND_EXERCISE_GOAL("goal을 다시 확인해주세요"),
    NOT_FOUND_EXERCISE_AREA("area를 다시 확인해주세요"),
    NOT_FOUND_EXERCISE_ITEM("item 을 찾을 수 없습니다"),

    // unknown
    UN_KNOWN_EXCEPTION("알수 없는 에러가 발생 하였습니다."),
}
