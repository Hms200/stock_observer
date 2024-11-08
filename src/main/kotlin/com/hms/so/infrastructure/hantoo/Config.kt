package com.hms.so.infrastructure.hantoo

import com.hms.so.domain.settings.SettingDto
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

const val REAL_DOMAIN = "https://openapi.koreainvestment.com:9443"
const val VIRTUAL_DOMAIN = "https://openapivts.koreainvestment.com:29443"

data class StockApiResponse<T>(
    val rt_cd: String,
    val msg_cd: String,
    val output: T,
)

@Component
class RequestHeader {
    var authorization: String = ""
    var appkey: String = ""
    var appsecret: String = ""
    val custtype: String = "P"

    fun setAuthorizations(values: SettingDto) {
        checkNotNull(values.accessToken)
        this.authorization = values.accessToken
        this.appkey = values.appKey
        this.appsecret = values.appSecret
    }

    fun getHeaders(): Map<String, String> {
        return hashMapOf(
            "authorization" to "Bearer " + this.authorization,
            "appkey" to this.appkey,
            "appsecret" to this.appsecret,
            "custtype" to this.custtype,
            "content-type" to "application/json; charset=utf-8"
        )
    }
}

@Configuration
class HantooConfig() {
    @Bean
    fun getWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl(REAL_DOMAIN)
            .build()
    }
}

