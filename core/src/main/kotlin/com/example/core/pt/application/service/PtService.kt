package com.example.core.pt.application.service

import com.example.common.mapper.convert
import com.example.core.ai.application.port.command.CallPtCommand
import com.example.core.ai.application.port.`in`.GeminiUseCase
import com.example.core.ai.application.service.GeneratePtService.GeneratePtResponseDto
import com.example.core.exercise.application.`in`.ExerciseItemUseCase
import com.example.core.pt.application.command.GeneratePtCommand
import com.example.core.pt.application.command.SavePtCommand
import com.example.core.pt.application.port.`in`.GeneratePtUseCase
import com.example.core.pt.application.port.out.PtJpaPort
import com.example.core.user.member.application.`in`.MemberUseCase
import com.example.domain.pt.AiPt
import com.google.gson.Gson
import org.springframework.stereotype.Service

@Service
class PtService(
    private val geminiUseCase: GeminiUseCase,
    private val memberUseCase: MemberUseCase,
    private val ptJpaPort: PtJpaPort,
    private val exerciseItemUseCase: ExerciseItemUseCase,
) : GeneratePtUseCase {
    override fun generatePt(command: GeneratePtCommand): AiPt {
        return ptJpaPort.findByThisWeek(command.memberId)?.consulting.convert() ?: run {
            val memberAndGoal = memberUseCase.getMemberAndGoal(command.memberId)
            val exerciseList = exerciseItemUseCase.findAll()
            val callPtCommand = CallPtCommand(
                goal = memberAndGoal.goal,
                exerciseMonths = memberAndGoal.exerciseMonths,
                tall = memberAndGoal.tall,
                weight = memberAndGoal.weight,
                age = memberAndGoal.age,
                gender = memberAndGoal.gender,
                day = command.day,
                time = command.time,
                exerciseItemList = exerciseList,
            )
            val response = geminiUseCase.callPt(callPtCommand)
            val dto = parseStringToJson(response)
            val savePtCommand = SavePtCommand(
                memberId = command.memberId,
                consulting = dto.toDomain(command.memberId),
            )
            return ptJpaPort.save(savePtCommand)
        }
    }

    private fun parseStringToJson(inputString: String): GeneratePtResponseDto {
        val jsonString = startExtractJsonString(inputString)
        val result = endExtractJsonString(jsonString)
        return Gson().fromJson(result, GeneratePtResponseDto::class.java)
    }

    private fun startExtractJsonString(inputString: String): String {
        val jsonStartIndex = inputString.indexOf("{")
        return inputString.substring(jsonStartIndex)
    }

    private fun endExtractJsonString(inputString: String): String {
        val jsonEndIndex = inputString.indexOf("```")
        return inputString.substring(0, jsonEndIndex)
    }
}
