package com.example.core.pt.port.out

import com.example.core.pt.command.SavePtCommand
import com.example.core.pt.model.AiPt
import java.util.UUID

interface PtJpaPort {
    fun save(command: SavePtCommand): AiPt

    fun findByThisWeek(memberId: UUID): AiPt?
}
