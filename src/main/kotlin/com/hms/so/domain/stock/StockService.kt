package com.hms.so.domain.stock

import com.hms.so.configuration.MessageCommand
import com.hms.so.configuration.StompMessage
import com.hms.so.configuration.logger
import com.hms.so.domain.stock.alarm.StockAskingPriceAlarm
import com.hms.so.domain.stock.alarm.StockPriceAlarm
import com.hms.so.infrastructure.hantoo.StockAskingPriceParams
import com.hms.so.infrastructure.hantoo.StockAskingPriceService
import com.hms.so.infrastructure.hantoo.StockPriceRequestParams
import com.hms.so.infrastructure.hantoo.StockPriceService
import kotlinx.coroutines.delay
import org.slf4j.Logger
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockPriceService: StockPriceService,
    private val stockAskingPriceService: StockAskingPriceService,
    private val messageSendingOperations: SimpMessageSendingOperations,
    private val stockPriceAlarm: StockPriceAlarm,
    private val stockAskingPriceAlarm: StockAskingPriceAlarm,
) {
    private var isGettingPrice: Boolean = false
    private var isGettingAskingPrice: Boolean = false

    val log: Logger = logger()

    suspend fun getStockPriceInfo(code: String) {
        isGettingPrice = true
        log.info("get StockPrice start")
        while (isGettingPrice) {
            try {
                log.info("attempting get price")
                val price = stockPriceService.getStockPrice(
                    StockPriceRequestParams(
                        fid_input_iscd = code
                    )
                )
                log.info(price.toString())
                messageSendingOperations.convertAndSend(
                    "/sub/price/$code", StompMessage(
                        code = code,
                        command = MessageCommand.START_GET_PRICE,
                        data = price
                    )
                )
                stockPriceAlarm.push(price!!.output)

            } catch (e: Error) {
                isGettingPrice = false
                stockPriceAlarm.stopObserving()
                break
            }
            delay(3000)
        }
    }

    suspend fun getStockAskingPriceInfo(code: String) {
        isGettingAskingPrice = true
        log.info("get StockAskingPrice start")
        while (isGettingAskingPrice) {
            try {
                log.info("attempting get asking price")
                val askingPrice = stockAskingPriceService.getStockAskingPrice(
                    StockAskingPriceParams(
                        fid_input_iscd = code
                    )
                )
                log.info(askingPrice.toString())
                messageSendingOperations.convertAndSend(
                    "/sub/asking_price/$code", StompMessage(
                        code = code,
                        command = MessageCommand.START_GET_ASK_PRICE,
                        data = askingPrice
                    )
                )
                stockAskingPriceAlarm.push(askingPrice!!.output)

            } catch (e: Error) {
                isGettingAskingPrice = false
                stockAskingPriceAlarm.stopObserving()
                break
            }
            delay(3000)
        }
    }

    fun stopGetPrice() {
        log.info("getStockPrice stopped")
        this.isGettingPrice = false
        stockPriceAlarm.stopObserving()
    }

    fun stopGetAskingPrice() {
        log.info("getAskingPrice stopped")
        this.isGettingAskingPrice = false
    }

}
