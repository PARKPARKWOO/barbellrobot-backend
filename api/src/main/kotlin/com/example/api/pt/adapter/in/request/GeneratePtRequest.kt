package com.example.api.pt.adapter.`in`.request

import com.example.core.pt.application.command.GeneratePtCommand
import java.util.UUID

data class GeneratePtRequest(
    val time: Int,
    val day: Int,
) {
    fun toCommand(memberId: UUID): GeneratePtCommand = GeneratePtCommand(
        memberId = memberId,
        time = time,
        day = day,
    )
}
