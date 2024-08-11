package com.example.application.event

import com.example.core.notification.model.SseEvent
import com.example.core.notification.model.SseEventType
import com.example.core.notification.port.out.NotificationPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class EventListener(
    private val notificationPort: NotificationPort,
) {
    private val coroutineDispatcher = Executors.newScheduledThreadPool(10).asCoroutineDispatcher()
    private val handlerScope = CoroutineScope(coroutineDispatcher + SupervisorJob())

    @EventListener
    fun listen(event: SseEvent) {
        handlerScope.launch {
            when (event.type) {
                SseEventType.RIVAL_ACCEPT -> {
                    notificationPort.sendRivalAccept(event.sender, event.receiver)
                }

                SseEventType.CHAT -> {
                    notificationPort.sendChatAlert(event.sender, event.receiver)
                }

                SseEventType.RIVAL_REQUEST -> {
                    notificationPort.sendRivalRequest(event.sender, event.receiver)
                }
            }
        }
    }
}
