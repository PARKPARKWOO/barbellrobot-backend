package com.example.core.history.port.command

import com.example.core.multimedia.dto.MultimediaDto
import java.util.UUID

// 점심, 저녁 에 해당하지 않은 오늘 운동기록과 history 를 기록 하는 command
data class RecordTodayHistoryCommand(
    val userId: UUID,
    val imageFiles: List<MultimediaDto>?,
    val videoFiles: List<MultimediaDto>?,
)
