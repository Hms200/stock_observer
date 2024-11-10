package com.hms.so.domain.stock

import com.hms.so.infrastructure.hantoo.StockPriceResponseOutput
import com.hms.so.infrastructure.slack.SlackApi
import com.hms.so.infrastructure.slack.SlackWebHookBody
import com.hms.so.infrastructure.slack.textBold
import org.springframework.stereotype.Service

enum class PriceMessage(val message: String) {
    START_OBSERVING("가격 감시 시작"),
    STOP_OBSERVING("가격 감시 종료"),
    TOUCH_MAX_PRICE("상한가 도달"),
    TOUCH_MIN_PRICE("하한가 도달"),
    BREAK_MAX_PRICE("상한가 이탈"),
    BREAK_MIN_PRICE("하한가 이탈"),
    CHANGE_SIGN_POSITIVE("양전"),
    CHANGE_SIGN_NEGATIVE("음전"),
    TOUCH_SET_HIGH_PRICE("목표가 도달"),
    TOUCH_SET_LOW_PRICE("손절가 도달"),
    TOUCH_SET_HIGH_RATE("목표수익률 도달"),
    TOUCH_SET_LOW_LATE("목표손절선 도달"),
}

// TODO: 조건설정, 조건 달성 후 알람. 목표가/손절가/목표수익률/목표손절선 등
@Service
class StockPriceAlarm(
    private val slackApi: SlackApi,
) : Alarm<StockPriceResponseOutput, PriceMessage> {
    override val list: MutableList<StockPriceResponseOutput> = mutableListOf()

    override fun stopObserving() {
        list.clear()
        sendMessage(
            SlackWebHookBody(text = PriceMessage.STOP_OBSERVING.message)
        )
    }

    override fun checkCondition(): PriceMessage? {
        if (list.size == 1) return PriceMessage.START_OBSERVING

        val last = list.last()
        val beforeLast = list[list.size - 2]

        if (last.prdy_vrss_sign == "1" && beforeLast.prdy_vrss_sign == "2") return PriceMessage.TOUCH_MAX_PRICE
        if (last.prdy_vrss_sign == "4" && beforeLast.prdy_vrss_sign == "5") return PriceMessage.TOUCH_MIN_PRICE
        if (last.prdy_vrss_sign == "2" && beforeLast.prdy_vrss_sign == "1") return PriceMessage.BREAK_MAX_PRICE
        if (last.prdy_vrss_sign == "5" && beforeLast.prdy_vrss_sign == "4") return PriceMessage.BREAK_MIN_PRICE
        if (last.prdy_vrss_sign == "2" && beforeLast.prdy_vrss_sign != "2") return PriceMessage.CHANGE_SIGN_POSITIVE
        if (last.prdy_vrss_sign == "5" && beforeLast.prdy_vrss_sign != "5") return PriceMessage.CHANGE_SIGN_NEGATIVE

        return null
    }

    override fun createMessage(outputData: StockPriceResponseOutput, message: PriceMessage): SlackWebHookBody {
        val sign: String =
            if (outputData.prdy_vrss_sign == "1" || outputData.prdy_vrss_sign == "2") {
                "+"
            } else if (outputData.prdy_vrss_sign == "3") {
                ""
            } else {
                "-"
            }
        val info = "현재가 : ${outputData.stck_prpr}원 $sign${outputData.prdy_vrss},  $sign${outputData.prdy_ctrt}%"

        return SlackWebHookBody(
            text = "${textBold(message.message)}\n$info"
        )
    }

    override fun sendMessage(message: SlackWebHookBody) {
        slackApi.sendWebHookMessage(message)
    }
}
