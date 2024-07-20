package com.example.core.pt.command

import com.example.core.pt.model.PtConsulting
import java.util.UUID

data class SavePtCommand(
    val memberId: UUID,
    val consulting: PtConsulting,
)
