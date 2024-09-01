package com.example.core.ai.port.out

import java.util.function.Function

interface GeminiGeneratePort<I, O> : Function<I, O>
