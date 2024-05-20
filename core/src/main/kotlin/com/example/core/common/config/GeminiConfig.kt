package com.example.core.common.config

import com.google.cloud.vertexai.VertexAI
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatClient
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
    fun chatClient(): VertexAiGeminiChatClient {
        val vertexAi = vertexAi()
        return VertexAiGeminiChatClient(
            vertexAi,
            VertexAiGeminiChatOptions.builder()
                .withModel(GEMINI_MODEL)
                .withTemperature(GEMINI_TEMPERATURE)
                .build(),
        )
    }

    companion object {
        const val GEMINI_MODEL = "gemini-1.5-flash-preview-0514"
        const val GEMINI_TEMPERATURE = 0.4F
    }
}
