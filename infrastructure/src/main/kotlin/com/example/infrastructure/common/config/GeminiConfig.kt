package com.example.infrastructure.common.config

import com.example.infrastructure.adapter.pt.GeneratePtAdapter
import com.google.cloud.vertexai.VertexAI
import org.springframework.ai.model.function.FunctionCallback
import org.springframework.ai.model.function.FunctionCallbackWrapper
import org.springframework.ai.model.function.FunctionCallbackWrapper.Builder.SchemaType.OPEN_API_SCHEMA
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GeminiConfig(
    @Value("\${spring.ai.vertex.ai.gemini.project-id}")
    val projectId: String,
    @Value("\${spring.ai.vertex.ai.gemini.location}")
    val location: String,
) {
    @Bean
    fun vertexAi(): VertexAI {
        return VertexAI(projectId, location)
    }

    @Bean
    fun chatClient(): VertexAiGeminiChatModel {
        val vertexAi = vertexAi()
        return VertexAiGeminiChatModel(
            vertexAi,
            VertexAiGeminiChatOptions.builder()
                .withModel(VertexAiGeminiChatModel.ChatModel.GEMINI_1_5_FLASH)
                .withTemperature(GEMINI_TEMPERATURE)
                .build(),
        )
    }

    @Bean
    fun ptFunctionCallback(): FunctionCallback {
        return FunctionCallbackWrapper.builder(GeneratePtAdapter())
            .withName(GeneratePtAdapter.FUNCTION_NAME)
            .withDescription("Generate pt for user")
            .withSchemaType(OPEN_API_SCHEMA)
            .build()
    }

    companion object {
        const val GEMINI_TEMPERATURE = 0.4F
    }
}
