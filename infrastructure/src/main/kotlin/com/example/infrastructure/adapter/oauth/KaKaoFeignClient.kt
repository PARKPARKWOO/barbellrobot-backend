package com.example.infrastructure.adapter.oauth

import com.example.common.constants.Constants
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kaKaoFeignClient", url = "https://kapi.kakao.com")
interface KaKaoFeignClient {
    @PostMapping(path = ["/oauth/token"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun getToken(
        @RequestHeader(name = "Content-type")
        type: String = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        @RequestParam("grant_type")
        grantType: String,
        @RequestParam("client_id")
        clientId: String,
        @RequestParam("redirect_uri")
        redirectUri: String,
        @RequestParam("code")
        code: String,
//         secret 설정할건지??
//        @RequestParam("client_secret") clientSecret: String,
    ): SignInKakaoResponse

    @GetMapping("/v1/user/access_token_info")
    fun getTokenInfo(
        @RequestHeader(name = Constants.AUTHORIZATION_HEADER)
        accessToken: String,
        @RequestHeader(name = "Content-type")
        type: String = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
    ): KakaoTokenInfo

    @GetMapping(path = ["/v2/user/me"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun getKakaoUserInfo(
        @RequestHeader(name = "Content-type")
        type: String = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        @RequestHeader(name = Constants.AUTHORIZATION_HEADER)
        accessToken: String,
    )
}

data class SignInKakaoResponse(
    @JsonProperty("token_type")
    val tokenType: String,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("refresh_token_expires_in")
    val refreshTokenExpiresIn: Int,
)

data class KakaoTokenInfo(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("expires_in")
    val expiresIn: Int,
    @JsonProperty("app_id")
    val appId: Int,
)
