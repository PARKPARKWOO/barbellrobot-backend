package com.example.core.history.application.port.out

interface UserHistoryJpaPort {
    fun attendanceToday(command: AttendanceTodayCommand)
}
