package com.example.core.pt.application.service

import com.example.core.ai.application.port.command.CallPtCommand
import com.example.core.ai.application.port.`in`.GeminiUseCase
import com.example.core.pt.application.command.GeneratePtCommand
import com.example.core.pt.application.command.SavePtCommand
import com.example.core.pt.application.port.`in`.GeneratePtUseCase
import com.example.core.pt.application.port.out.PtJpaPort
import com.example.core.user.member.application.`in`.MemberUseCase
import org.springframework.stereotype.Service

@Service
class PtService(
    private val geminiUseCase: GeminiUseCase,
    private val memberUseCase: MemberUseCase,
    private val ptJpaPort: PtJpaPort,
) : GeneratePtUseCase {
    override fun generatePt(command: GeneratePtCommand): String {
        val memberAndGoal = memberUseCase.getMemberAndGoal(command.memberId)
        val callPtCommand = CallPtCommand(
            goal = memberAndGoal.goal,
            exerciseMonths = memberAndGoal.exerciseMonths,
            tall = memberAndGoal.tall,
            weight = memberAndGoal.weight,
            age = memberAndGoal.age,
            gender = memberAndGoal.gender,
            day = command.day,
            time = command.time,
        )
        val response = geminiUseCase.callPt(callPtCommand)
        val savePtCommand = SavePtCommand(
            memberId = command.memberId,
            content = response,
        )
        ptJpaPort.save(savePtCommand)
        return response
    }
}
