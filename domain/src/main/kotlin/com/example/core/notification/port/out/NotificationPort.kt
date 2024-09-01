package com.example.core.notification.port.out

import com.example.core.event.NotificationEvent

interface NotificationPort {
    /* sender = 수락 하는사람 (요청을 받은 쪽) */
    fun sendNotification(notificationEvent: NotificationEvent): Boolean
}
