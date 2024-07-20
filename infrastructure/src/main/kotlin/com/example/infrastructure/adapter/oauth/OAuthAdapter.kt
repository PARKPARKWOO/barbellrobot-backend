package com.example.infrastructure.adapter.oauth

import com.example.core.sign.dto.KaKaoTokenInfo
import com.example.core.sign.port.out.KaKaoSignInPort
import org.springframework.stereotype.Component

@Component
class OAuthAdapter(
    private val kaKaoFeignClient: KaKaoFeignClient,
) : KaKaoSignInPort {
    override fun getTokenInfo(accessToken: String): KaKaoTokenInfo = kaKaoFeignClient.getTokenInfo(
        accessToken = accessToken,
    ).toModel()

    override fun getKakaoUserInfo(accessToken: String) {
        kaKaoFeignClient.getKakaoUserInfo(
            accessToken = accessToken,
        )
    }
}
