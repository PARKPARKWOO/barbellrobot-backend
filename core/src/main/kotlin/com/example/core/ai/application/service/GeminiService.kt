package com.example.core.ai.application.service

import com.example.core.ai.application.port.command.CallPtCommand
import com.example.core.ai.application.port.`in`.GeminiUseCase
import com.example.domain.constants.PromptConstants
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatClient
import org.springframework.stereotype.Service

@Service
class GeminiService(
    private val chatClient: VertexAiGeminiChatClient,
) : GeminiUseCase {
    override fun callPt(command: CallPtCommand): String {
        val prompt = PromptConstants.ptPromptTemplate(
            gender = command.gender,
            goal = command.goal,
            exerciseMonths = command.exerciseMonths,
            tall = command.tall,
            weight = command.weight,
            age = command.age,
            day = command.day,
            time = command.time,
        )
        return chatClient.call(prompt)
    }
}
