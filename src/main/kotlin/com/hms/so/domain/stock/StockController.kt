package com.hms.so.domain.stock

import com.hms.so.common.ResponseObject
import com.hms.so.common.logger
import com.hms.so.infrastructure.hantoo.StockInfoRequestParams
import com.hms.so.infrastructure.hantoo.StockInfoResponse
import com.hms.so.infrastructure.hantoo.StockInfoService
import org.slf4j.Logger
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("stock")
@CrossOrigin("*")
class StockController(
    private val stockInfoService: StockInfoService
) {
    val log: Logger = logger()

    @GetMapping("search")
    fun getStockInfo(@RequestParam pdno: String): ResponseObject<StockInfoResponse> {
        if (pdno == "") return ResponseObject(success = false, errorMessage = "종목번호 누락")
        return try {
            val result = stockInfoService.getStockInfo(
                StockInfoRequestParams(pdno = pdno)
            )
            log.info("StockController::Search Result::" + result.toString())
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
}
