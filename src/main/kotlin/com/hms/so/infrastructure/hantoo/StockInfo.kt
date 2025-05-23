package com.hms.so.infrastructure.hantoo

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono

const val SEARCH_STOCK_INFO_URI = "/uapi/domestic-stock/v1/quotations/search-stock-info"
const val SEARCH_STOCK_TR_ID = "CTPF1002R"

data class StockInfoRequestParams(
    // 주식으로 고정됨.
    val prdt_type_cd: String? = "300",
    val pdno: String,
)

data class StockInfoResponseOutput(
    // 상품번호
    val pdno: String,
    // 상품유형코드
    val prdt_type_cd: String,
    // 상품명
    val prdt_name: String,
    // 거래정지여부
    val tr_stop_yn: String,
    // 관리종목여부
    val admn_item_yn: String,
    // 당일종가
    val thdt_clpr: String,
    // 전일종가
    val bfdy_clpr: String,
)

@Service
class StockInfoService(
    private val requestHeader: RequestHeader,
    private val webClient: WebClient,
) {
    fun getStockInfo(params: StockInfoRequestParams): StockApiResponse<StockInfoResponseOutput>? {
        return webClient
            .get()
            .uri { uriBuilder ->
                uriBuilder.path(SEARCH_STOCK_INFO_URI)
                    .queryParam("PRDT_TYPE_CD", params.prdt_type_cd)
                    .queryParam("PDNO", params.pdno)
                    .build()
            }
            .headers {
                requestHeader.getHeaders()
                    .map { h -> it[h.key] = h.value }
                it["tr_id"] = SEARCH_STOCK_TR_ID
            }
            .retrieve()
            .bodyToMono<StockApiResponse<StockInfoResponseOutput>>()
            .block()
    }
}
