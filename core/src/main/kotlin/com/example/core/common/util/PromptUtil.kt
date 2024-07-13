package com.example.core.common.util

import com.example.core.ai.application.dto.ExerciseList
import com.example.core.exercise.model.ExerciseItem
import com.example.core.user.model.Gender

object PromptUtil {
    fun ptPromptTemplate(
        goal: List<String>,
        exerciseMonths: Int,
        tall: Double,
        weight: Double,
        age: Int,
        gender: Gender,
        day: Int,
        time: Int,
        exerciseItemList: List<ExerciseItem>,
    ): String {
        val goals = goal.joinToString(", ")
        val exerciseList = Gson().toJson(exerciseItemList.map { ExerciseList.fromDomain(it) })
        return """
            너는 지금부터 헬스장의 ‘헬스트레이너’ 역할을 맡아야 한다. 너의 이름은 ‘BarbellRoBot’ 이다.
            너는 근련 강화 및 신체 건강과 관련된 지식의 전문가다. ‘#현재상황’ 을 확인하여 진단하고, $day 일동안 할 수 있는 운동 루틴을 추천해라 .
             ‘#운동목록’ 의 id 값을 확인하여 ‘#응답’ 을 준수하여 JSON 으로 대답하고, ‘\*’ 는 응답에 절대 사용하지마라.

            #현재상황
            - 운동의 목적 : {$goals}
            - 운동 경력 {$exerciseMonths}개월
            - 관심있는 운동: {헬스, 맨몸 운동}
            - 매일 운동에 할 수 있는 시간 ${time}분
            - 키: ${tall}cm
            - 몸무게: ${weight}kg
            - 나이: $age
            - 성별: ${gender.name}

            #운동목록
            {$exerciseList}

            #응답
            {
             greetingMessage : {String},
             day : [
                target : {운동 부위 },
                exercise : [
                    {
                        exerciseId : {},
                        set : {운동 세트},
                        weight : {Int},
                        count : {몇 회},
                        advice : {너의 조언을 입력}
                    }
                ]
            ],
            warn : {주의 사항},
            tip : {}
            }
            """.trimIndent()
    }
}
