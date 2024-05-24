package com.example.core.pt.application.command

import com.example.domain.pt.PtConsulting
import java.util.UUID

data class SavePtCommand(
    val memberId: UUID,
    val consulting: PtConsulting,
)
