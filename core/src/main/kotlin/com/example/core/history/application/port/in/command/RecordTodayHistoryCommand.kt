package com.example.core.history.application.port.`in`.command

import org.springframework.web.multipart.MultipartFile
import java.util.UUID

// 점심, 저녁 에 해당하지 않은 오늘 운동기록과 history 를 기록 하는 command
data class RecordTodayHistoryCommand(
    val userId: UUID,
    val imageFiles: List<MultipartFile>?,
    val videoFiles: List<MultipartFile>?,
)
