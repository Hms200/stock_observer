package com.hms.so.infrastructure.hantoo

import com.hms.so.configuration.logger
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

const val STOCK_ASKING_PRICE_URI = "/uapi/domestic-stock/v1/quotations/inquire-asking-price-exp-ccn"
const val STOCK_ASKING_PRICE_TR_ID = "FHKST01010200"

data class StockAskingPriceParams(
    // 고정값
    val fid_cond_mrkt_div_code: String? = "J",
    val fid_input_iscd: String,
)

data class StockAskingPriceOutput(
    // 총 매도호가 잔량
    val total_askp_rsqn: String,
    // 총 매수호가 잔량
    val total_bidp_rsqn: String,
    // 순매수 호가 잔량
    val ntby_aspr_rsqn: String,
)

@Service
class StockAskingPriceService(
    private val requestHeader: RequestHeader,
    private val webClient: WebClient,
) {
    val log: Logger = logger()

    fun getStockAskingPrice(params: StockAskingPriceParams): StockApiResponse<StockAskingPriceOutput>? {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path(STOCK_ASKING_PRICE_URI)
                    .queryParam("FID_COND_MRKT_DIV_CODE", params.fid_cond_mrkt_div_code)
                    .queryParam("FID_INPUT_ISCD", params.fid_input_iscd)
                    .build()
            }
            .headers {
                requestHeader.getHeaders()
                    .map { h -> it[h.key] = h.value }
                it["tr_id"] = STOCK_ASKING_PRICE_TR_ID
            }
            .retrieve()
            .bodyToMono<StockApiResponse<StockAskingPriceOutput>>()
            .onErrorResume { e ->
                log.error(e.message)
                Mono.error(e)
            }
            .block()
    }

}
