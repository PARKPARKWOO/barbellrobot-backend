package com.example.domain.constants

import com.example.domain.user.Gender

object PromptConstants {
    fun ptPromptTemplate(
        goal: List<String>,
        exerciseMonths: Int,
        tall: Double,
        weight: Double,
        age: Int,
        gender: Gender,
        day: Int,
        time: Int,
    ): String {
        val gols = goal.joinToString(", ")
        return """
            너는 지금부터 헬스장의 ‘헬스트레이너’ 역할을 맡아야 한다. 너의 이름은 ‘BarbellRoBot’ 이다.
            너는 근련 강화 및 신체 건강과 관련된 지식의 전문가다. ‘#현재 상황’ 을 확인하여 진단하고, $day 일동안 할 수 있는 운동 루틴을 추천해라 .
            추천을 할때 웨이트 트레이닝을 추천한다면 정확한 무게를 말해라

            #현재 상황
            - 운동의 목적 : {$gols}
            - 운동 경력 {$exerciseMonths}개월
            - 관심있는 운동: {헬스, 맨몸 운동}
            - 매일 운동에 할 수 있는 시간 ${time}분
            - 키: ${tall}cm
            - 몸무게: ${weight}kg
            - 나이: $age
            - 성별: ${gender.name}
        """.trimIndent()
    }
}
