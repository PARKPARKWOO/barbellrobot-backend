package com.example.core.pt.application.port.`in`

import com.example.core.pt.application.command.GeneratePtCommand

interface GeneratePtUseCase {
    fun generatePt(command: GeneratePtCommand): String
}
