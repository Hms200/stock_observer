package com.hms.so.infrastructure.hantoo

import com.hms.so.configuration.logger
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

const val STOCK_PRICE_URI = "/uapi/domestic-stock/v1/quotations/inquire-price"
const val STOCK_PRICE_TR_ID = "FHKST01010100"

data class StockPriceRequestParams(
    // 시장분류 코드  // J: 주식, ETN, ETF    // W: ELW
    val fid_cond_mrkt_div_code: String? = "J",
    // 종목코드
    val fid_input_iscd: String,
)

data class StockPriceResponseOutput(
    // 현재가
    val stck_prpr: String,
    // 전일 대비
    val prdy_vrss: String,
    // 전일 대비 부호 1:상 2: 상승 3: 보합: 4: 하 5: 하락
    val prdy_vrss_sign: String,
    // 전일 대비율
    val prdy_ctrt: String,
    // 시가
    val stck_oprc: String,
    // 최고가
    val stck_hgpr: String,
    // 최저가
    val stck_lwpr: String,
    // 상한가
    val stck_mxpr: String,
    // 하한가
    val stck_llam: String,
)

@Service
class StockPriceService(
    private val requestHeader: RequestHeader,
    private val webClient: WebClient,
) {
    val log: Logger = logger()

    fun getStockPrice(params: StockPriceRequestParams): StockApiResponse<StockPriceResponseOutput>? {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path(STOCK_PRICE_URI)
                    .queryParam("FID_COND_MRKT_DIV_CODE", params.fid_cond_mrkt_div_code)
                    .queryParam("FID_INPUT_ISCD", params.fid_input_iscd)
                    .build()
            }
            .headers {
                requestHeader.getHeaders()
                    .map { h -> it[h.key] = h.value }
                it["tr_id"] = STOCK_PRICE_TR_ID
            }
            .retrieve()
            .bodyToMono<StockApiResponse<StockPriceResponseOutput>>()
            .onErrorResume { e ->
                log.error(e.message)
                Mono.error(e)
            }
            .block()
    }
}
