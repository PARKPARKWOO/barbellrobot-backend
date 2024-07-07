package com.example.core.pt.application.port.`in`

import com.example.core.pt.application.command.GeneratePtCommand
import com.example.core.pt.model.AiPt
import java.util.UUID

interface GeneratePtUseCase {
    fun generatePt(command: GeneratePtCommand): AiPt

    fun getPt(memberId: UUID): AiPt?
}
