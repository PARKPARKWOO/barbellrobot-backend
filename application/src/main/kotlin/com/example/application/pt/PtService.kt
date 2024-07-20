package com.example.application.pt

import com.example.application.common.transaction.Tx
import com.example.core.ai.port.command.CallPtCommand
import com.example.core.ai.port.`in`.GeminiUseCase
import com.example.core.exercise.port.`in`.ExerciseItemUseCase
import com.example.core.pt.command.GeneratePtCommand
import com.example.core.pt.command.SavePtCommand
import com.example.core.pt.port.`in`.GeneratePtUseCase
import com.example.core.pt.port.out.PtJpaPort
import com.example.core.pt.model.AiPt
import com.example.core.pt.model.PtConsulting
import com.example.core.user.port.`in`.MemberUseCase
import com.google.gson.Gson
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class PtService(
    private val geminiUseCase: GeminiUseCase,
    private val memberUseCase: MemberUseCase,
    private val ptJpaPort: PtJpaPort,
    private val exerciseItemUseCase: ExerciseItemUseCase,
) : GeneratePtUseCase {
    override fun generatePt(command: GeneratePtCommand): AiPt {
        return ptJpaPort.findByThisWeek(command.memberId) ?: run {
            val memberAndGoal = memberUseCase.getMemberAndGoal(command.memberId)
            val exerciseList = exerciseItemUseCase.findAll()
            val callPtCommand = CallPtCommand(
                goal = memberAndGoal.memberGoal,
                exerciseMonths = memberAndGoal.memberDetailQueryDto!!.memberInfoQueryDto!!.exerciseMonths!!,
                tall = memberAndGoal.memberDetailQueryDto!!.memberInfoQueryDto!!.tall!!,
                weight = memberAndGoal.memberDetailQueryDto!!.memberInfoQueryDto!!.weight!!,
                age = memberAndGoal.memberDetailQueryDto!!.memberInfoQueryDto!!.age!!,
                gender = memberAndGoal.memberDetailQueryDto!!.memberInfoQueryDto!!.gender!!,
                day = command.day,
                time = command.time,
                exerciseItemList = exerciseList,
            )
            val response = geminiUseCase.callPt(callPtCommand)
            val dto = parseStringToJson(response)
            val savePtCommand = SavePtCommand(
                memberId = command.memberId,
                consulting = dto,
            )
            return ptJpaPort.save(savePtCommand)
        }
    }

    override fun getPt(memberId: UUID): AiPt? {
        return Tx.readTx { ptJpaPort.findByThisWeek(memberId) }
    }

    private fun parseStringToJson(inputString: String): PtConsulting {
        val jsonString = startExtractJsonString(inputString)
        val result = endExtractJsonString(jsonString)
        return Gson().fromJson(result, PtConsulting::class.java)
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
