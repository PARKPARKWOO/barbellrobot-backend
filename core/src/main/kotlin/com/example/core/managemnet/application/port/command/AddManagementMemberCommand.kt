package com.example.core.managemnet.application.port.command

import java.util.UUID

data class AddManagementMemberCommand(
    val trainerId: UUID,
    val memberId: UUID,
)
