package com.hms.so.configuration

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.converter.MessageConverter
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

enum class MessageCommand {
    START_GET_PRICE,
    STOP_GET_PRICE,
    START_GET_ASK_PRICE,
    STOP_GET_ASK_PRICE;
}

data class StompMessage<T>(
    @JsonProperty("code")
    val code: String,
    @JsonProperty("command")
    val command: MessageCommand?,
    @JsonProperty("data")
    val data: T? = null
)

@Configuration
@EnableWebSocketMessageBroker
class WebsocketMessageBrokerConfig() : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/sub")
        registry.setApplicationDestinationPrefixes("/pub")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/ws")
            .setAllowedOrigins("*")
    }

    override fun configureMessageConverters(messageConverters: MutableList<MessageConverter>): Boolean {
        messageConverters.add(
            MappingJackson2MessageConverter().apply {
                this.setPrettyPrint(true)
            }
        )
        return false
    }
}
