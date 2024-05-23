package com.example.core.ai.application.service

import com.example.core.ai.application.port.command.CallPtCommand
import com.example.core.ai.application.port.`in`.GeminiUseCase
import com.example.core.common.util.PromptUtil
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatClient
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions
import org.springframework.stereotype.Service

@Service
class GeminiService(
    private val chatClient: VertexAiGeminiChatClient,
) : GeminiUseCase {
    override fun callPt(command: CallPtCommand): String {
        val userMessage = UserMessage(
            PromptUtil.ptPromptTemplate(
                gender = command.gender,
                goal = command.goal,
                exerciseMonths = command.exerciseMonths,
                tall = command.tall,
                weight = command.weight,
                age = command.age,
                day = command.day,
                time = command.time,
                exerciseItemList = command.exerciseItemList,
            ),
        )
        val result = chatClient.call(
            Prompt(
                listOf(userMessage),
                VertexAiGeminiChatOptions.builder()
                    .withFunction(GeneratePtService.FUNCTION_NAME)
                    .build(),
            ),
        )
        return result.result.output.content
    }
}
