package com.example.infrastructure.adapter.notification

import com.example.core.notification.model.SseEvent
import com.example.core.notification.model.SseEventType.CHAT
import com.example.core.notification.model.SseEventType.RIVAL_ACCEPT
import com.example.core.notification.model.SseEventType.RIVAL_PROD
import com.example.core.notification.model.SseEventType.RIVAL_REQUEST
import com.example.core.notification.port.out.NotificationPort
import com.example.infrastructure.adapter.notification.SseConnectionRegistry.SSE_CONNECTED_USER
import org.springframework.stereotype.Component
import java.util.UUID

/* 굳이 나눌 필요가 있을지? 그리고 sseEvent Domain 을 parameter 로 입력 받는것 고려 */
@Component
class NotificationSender : NotificationPort {
    override fun sendRivalAccept(sender: UUID, receiver: UUID) {
        SSE_CONNECTED_USER[receiver]?.send(
            SseEvent(
                sender = sender,
                receiver = receiver,
                type = RIVAL_ACCEPT,
                message = "",
            ),
        )
    }

    override fun sendChatAlert(sender: UUID, receiver: UUID) {
        SSE_CONNECTED_USER[receiver]?.send(
            SseEvent(
                sender = sender,
                receiver = receiver,
                type = CHAT,
                message = "",
            ),
        )
    }

    override fun sendRivalRequest(sender: UUID, receiver: UUID) {
        SSE_CONNECTED_USER[receiver]?.send(
            SseEvent(
                sender = sender,
                receiver = receiver,
                type = RIVAL_REQUEST,
                message = "",
            ),
        )
    }

    override fun sendRivalProdding(sender: UUID, receiver: UUID) {
        SSE_CONNECTED_USER[receiver]?.send(
            SseEvent(
                sender = sender,
                receiver = receiver,
                type = RIVAL_PROD,
                message = "",
            ),
        )
    }
}
