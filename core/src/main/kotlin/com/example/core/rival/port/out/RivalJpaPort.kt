package com.example.core.rival.port.out

import com.example.core.rival.dto.RivalSummaryDto
import com.example.core.rival.port.command.RivalEventCommand
import java.util.UUID

interface RivalJpaPort {
    fun save(memberId: UUID)

    fun refuseFromRivalRequest(command: RivalEventCommand)

    fun requestRival(command: RivalEventCommand)

    fun acceptFromRivalRequest(command: RivalEventCommand)

    fun findMyRivals(memberId: UUID): List<RivalSummaryDto>

    fun findPendingFromMe(memberId: UUID): List<RivalSummaryDto>
}
