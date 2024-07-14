package com.example.application.ai

import com.example.core.ai.dto.GeminiConvertible
import com.example.core.ai.port.out.GeminiGeneratePort

abstract class GeminiGenerateContent<I, O : GeminiConvertible<D>, D> : GeminiGeneratePort<I, O> {
    abstract override fun apply(t: I): O
}
