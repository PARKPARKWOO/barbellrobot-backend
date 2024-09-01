package com.example.application.event

import com.example.core.event.NotificationEvent
import com.example.core.event.SseConnectEvent
import com.example.core.event.SseConnectType
import com.example.core.notification.port.`in`.NotificationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class EventListener(
    private val notificationUseCase: NotificationUseCase,
) {
    private val coroutineDispatcher = Executors.newScheduledThreadPool(10).asCoroutineDispatcher()
    private val handlerScope = CoroutineScope(coroutineDispatcher)

    @EventListener
    fun listenForNotification(event: NotificationEvent) {
        handlerScope.launch {
            notificationUseCase.sendNotification(event)
        }
    }

    @EventListener
    fun listenForSseConnected(event: SseConnectEvent) {
        handlerScope.launch {
            when (event.type) {
                SseConnectType.CONNECTED -> {
                    val unreadNotifications = notificationUseCase.findRecentUnreadNotification(event.userId)
                    unreadNotifications.forEach { notification ->
                        notificationUseCase.sendNotification(notification)
                    }
                }

                SseConnectType.DISCONNECTED -> {
                    // 재연결 시도?
                }
            }
        }
    }
}
