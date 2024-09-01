package com.example.core.sign.port.out

import com.example.core.sign.dto.KaKaoTokenInfo

interface KaKaoSignInPort {
    fun getTokenInfo(
        accessToken: String,
    ): KaKaoTokenInfo

    fun getKakaoUserInfo(
        accessToken: String,
    )
}
