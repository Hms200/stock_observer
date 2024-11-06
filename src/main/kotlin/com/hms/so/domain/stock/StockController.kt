package com.hms.so.domain.stock

import com.hms.so.common.ResponseObject
import com.hms.so.infrastructure.hantoo.StockInfoRequestParams
import com.hms.so.infrastructure.hantoo.StockInfoResponse
import com.hms.so.infrastructure.hantoo.StockInfoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("stock")
@CrossOrigin("*")
class StockController(
    private val stockInfoService: StockInfoService
) {
    @GetMapping("search")
    fun getStockInfo(@RequestParam pdno: String): ResponseObject<StockInfoResponse> {
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
}
