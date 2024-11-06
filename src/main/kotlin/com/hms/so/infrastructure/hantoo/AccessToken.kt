package com.hms.so.infrastructure.hantoo

import com.hms.so.domain.settings.SettingDto
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

const val ACCESS_TOKEN_REQUEST_RUI = "/oauth2/tokenP"

data class AccessTokenRequest(
    val grant_type: String = "client_credentials",
    val appkey: String,
    val appsecret: String,
)

data class AccessTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val access_token_token_expired: String,
)

@Service
class AccessTokenService() {

    val client = WebClient.builder()
        .baseUrl(VIRTUAL_DOMAIN)
        .defaultHeaders {
            it.contentType = MediaType.APPLICATION_JSON_UTF8
        }
        .build()

    fun getAccessToken(settingDto: SettingDto): AccessTokenResponse? {
        val body = AccessTokenRequest(
            appkey = settingDto.appKey,
            appsecret = settingDto.appSecret
        )
        return client
            .post()
            .uri(ACCESS_TOKEN_REQUEST_RUI)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(AccessTokenResponse::class.java)
            .block()
    }
}
