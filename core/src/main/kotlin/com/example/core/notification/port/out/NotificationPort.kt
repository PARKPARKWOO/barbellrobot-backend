package com.example.core.notification.port.out

import java.util.UUID

interface NotificationPort {
    /* sender = 수락 하는사람 (요청을 받은 쪽) */
    fun sendRivalAccept(sender: UUID, receiver: UUID)

    fun sendChatAlert(sender: UUID, receiver: UUID)

    fun sendRivalRequest(sender: UUID, receiver: UUID)
}
