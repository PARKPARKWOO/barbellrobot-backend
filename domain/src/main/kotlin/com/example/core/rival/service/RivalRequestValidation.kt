package com.example.core.rival.service

import com.example.core.common.error.ErrorCode.CANNOT_REQUEST_RIVAL_TO_SELF
import com.example.core.common.error.ServiceException
import java.util.UUID

class RivalRequestValidation {
    fun isRequestToSelf(senderId: UUID, receiverId: UUID) {
        if (senderId == receiverId) throw ServiceException(CANNOT_REQUEST_RIVAL_TO_SELF)
    }
}
