package com.hms.so.domain.stock

import com.hms.so.common.ResponseObject
import com.hms.so.common.logger
import com.hms.so.configuration.MessageCommand
import com.hms.so.configuration.StompMessage
import com.hms.so.infrastructure.hantoo.*
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("stock")
@CrossOrigin("*")
class StockController(
    private val stockInfoService: StockInfoService,
    private val stockService: StockService,
) {
    val log: Logger = logger()

    @GetMapping("search")
    fun getStockInfo(@RequestParam pdno: String): ResponseObject<StockApiResponse<StockInfoResponseOutput>> {
        if (pdno == "") return ResponseObject(success = false, errorMessage = "종목번호 누락")
        return try {
            val result = stockInfoService.getStockInfo(
                StockInfoRequestParams(pdno = pdno)
            )
            ResponseObject(
                success = true,
                body = result,
            )
        } catch (e: Error) {
            ResponseObject(
                success = false,
                errorMessage = e.message
            )
        }
    }

    @MessageMapping("price")
    fun getPriceMessage(message: StompMessage<String>) {
        log.info("getPriceMessage invoked with parameter message:$message")
        if (message.command == MessageCommand.START_GET_PRICE) {
            runBlocking {
                stockService.getStockPriceInfo(code = message.code)
            }
        } else if (message.command == MessageCommand.STOP_GET_PRICE) {
            stockService.stopGetPrice()
        }

    }
}
