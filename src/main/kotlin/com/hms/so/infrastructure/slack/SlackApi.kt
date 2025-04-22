package com.hms.so.infrastructure.slack

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

// confidential
const val SLACK_WEB_HOOK_URL = ""

data class SlackWebHookBody(
    val text: String
)

@Service
class SlackApi() {
    val client: WebClient = WebClient.builder()
        .build()

    fun sendWebHookMessage(body: SlackWebHookBody) {
        client.post()
            .uri(SLACK_WEB_HOOK_URL)
            .bodyValue(body)
            .retrieve()
            .bodyToMono<String>()
            .block()
    }
}

fun textBold(text: String) = "*$text*"
