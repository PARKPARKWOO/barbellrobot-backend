package com.example.infrastructure.adapter.notification

import com.example.core.event.NotificationEvent
import com.example.core.notification.port.out.NotificationPort
import com.example.infrastructure.adapter.notification.SseConnectionRegistry.SSE_CONNECTED_USER
import org.springframework.stereotype.Component

/* 굳이 나눌 필요가 있을지? 그리고 sseEvent Domain 을 parameter 로 입력 받는것 고려 */
@Component
class NotificationSender : NotificationPort {
    override fun sendNotification(notificationEvent: NotificationEvent): Boolean {
        return SSE_CONNECTED_USER[notificationEvent.receiver]?.let {
            it.send(notificationEvent)
            true
        } ?: false
    }
}
