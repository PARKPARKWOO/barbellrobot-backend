package com.example.core.pt.application.port.out

import com.example.core.pt.adapter.out.persistence.entity.AiPtModel
import com.example.core.pt.application.command.SavePtCommand
import java.util.UUID

interface PtJpaPort {
    fun save(command: SavePtCommand)

    fun findByThisWeek(memberId: UUID): AiPtModel?
}
