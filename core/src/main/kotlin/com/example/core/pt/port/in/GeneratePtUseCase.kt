package com.example.core.pt.port.`in`

import com.example.core.pt.command.GeneratePtCommand
import com.example.core.pt.model.AiPt
import java.util.UUID

interface GeneratePtUseCase {
    fun generatePt(command: GeneratePtCommand): AiPt

    fun getPt(memberId: UUID): AiPt?
}
