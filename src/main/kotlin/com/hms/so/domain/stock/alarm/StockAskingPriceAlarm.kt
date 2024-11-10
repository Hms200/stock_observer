package com.hms.so.domain.stock.alarm

import com.hms.so.infrastructure.hantoo.StockAskingPriceOutput
import com.hms.so.infrastructure.slack.SlackApi
import com.hms.so.infrastructure.slack.SlackWebHookBody
import com.hms.so.infrastructure.slack.textBold
import org.springframework.stereotype.Service

enum class AskingPriceMessage(val message: String) {
    START_OBSERVING("호가감시 시작"),
    STOP_OBSERVING("호가감시 종료"),
    NET_BUY_ASKING_POSITIVE("매수우위"),
    NET_BUY_ASKING_NEGATIVE("매도우위"),
    SIGN_CHANGED_POSITIVE("매수우위 전환"),
    SIGN_CHANGED_NEGATIVE("매도우위 전환"),
}

@Service
class StockAskingPriceAlarm(
    private val slackApi: SlackApi,
) : Alarm<StockAskingPriceOutput, AskingPriceMessage> {
    override val list: MutableList<StockAskingPriceOutput> = mutableListOf()

    override fun stopObserving() {
        list.clear()
        sendMessage(
            SlackWebHookBody(text = AskingPriceMessage.STOP_OBSERVING.message)
        )
    }

    override fun checkCondition(): AskingPriceMessage? {
        if (list.size == 1) return AskingPriceMessage.START_OBSERVING

        val last = list.last()
        val beforeLast = list[list.size - 2]
        val signOfLast = last.ntby_aspr_rsqn.substring(0, 1)
        val signOfBeforeLast = beforeLast.ntby_aspr_rsqn.substring(0, 1)

        if (signOfBeforeLast != "-" && signOfLast == "-") return AskingPriceMessage.SIGN_CHANGED_NEGATIVE
        if (signOfBeforeLast == "-" && signOfLast != "-") return AskingPriceMessage.SIGN_CHANGED_POSITIVE

        return null
    }

    override fun createMessage(outputData: StockAskingPriceOutput, message: AskingPriceMessage): SlackWebHookBody {
        return SlackWebHookBody(
            text = "*${textBold(message.message)}\n" +
                    "매도잔량: ${outputData.total_askp_rsqn}, 매수잔량: ${outputData.total_bidp_rsqn}\n" +
                    "순 매수잔량: ${outputData.ntby_aspr_rsqn}"
        )
    }

    override fun sendMessage(message: SlackWebHookBody) {
        slackApi.sendWebHookMessage(message)
    }
}
