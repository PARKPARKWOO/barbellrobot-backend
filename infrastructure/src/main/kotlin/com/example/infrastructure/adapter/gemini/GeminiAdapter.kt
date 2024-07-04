package com.example.infrastructure.adapter.gemini

import com.example.core.ai.port.command.CallPtCommand
import com.example.core.ai.port.`in`.GeminiUseCase
import com.example.infrastructure.adapter.pt.GeneratePtAdapter
import com.example.infrastructure.common.config.GeminiConfig
import com.example.infrastructure.common.util.PromptUtil
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions
import org.springframework.stereotype.Component

@Component
class GeminiAdapter(
    private val chatClient: VertexAiGeminiChatModel,
    private val geminiConfig: GeminiConfig,
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
                    .withFunctionCallbacks(listOf(geminiConfig.ptFunctionCallback()))
                    .withFunction(GeneratePtAdapter.FUNCTION_NAME)
                    .build(),
            ),
        )
        return result.result.output.content
    }
}
