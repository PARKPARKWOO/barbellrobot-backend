package com.example.core.rival.port.`in`

import com.example.core.rival.dto.RivalSummaryDto
import com.example.core.rival.port.command.RivalEventCommand
import java.util.UUID

interface RivalUseCase {
    fun createRival(memberId: UUID)

    fun getMyRivals(memberId: UUID): List<RivalSummaryDto>

    fun getPendingRivalList(memberId: UUID): List<RivalSummaryDto>

    fun updateRivalStatus(command: RivalEventCommand)
}
