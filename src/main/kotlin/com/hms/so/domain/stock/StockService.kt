package com.hms.so.domain.stock

import com.hms.so.common.logger
import com.hms.so.configuration.MessageCommand
import com.hms.so.configuration.StompMessage
import com.hms.so.infrastructure.hantoo.StockApiResponse
import com.hms.so.infrastructure.hantoo.StockPriceRequestParams
import com.hms.so.infrastructure.hantoo.StockPriceResponseOutput
import com.hms.so.infrastructure.hantoo.StockPriceService
import kotlinx.coroutines.delay
import org.slf4j.Logger
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockPriceService: StockPriceService,
    private val messageSendingOperations: SimpMessageSendingOperations
) {
    private var isGettingPrice: Boolean = false

    val log: Logger = logger()

    suspend fun getStockPriceInfo(code: String) {
        isGettingPrice = true
        log.info("getStockPrice start")
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
                    "/sub/price/$code", StompMessage<StockApiResponse<StockPriceResponseOutput>>(
                        code = code,
                        MessageCommand.START_GET_PRICE,
                        data = price
                    )
                )

            } catch (e: Error) {
                isGettingPrice = false
                break
            }
            delay(3000)
        }
    }

    fun stopGetPrice() {
        log.info("getStockPrice stopped")
        this.isGettingPrice = false
    }

}
