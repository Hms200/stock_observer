package com.hms.so.alarm

import com.hms.so.domain.stock.alarm.StockPriceAlarm
import com.hms.so.infrastructure.hantoo.StockPriceResponseOutput
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AlarmTest(
    @Autowired
    val alarm: StockPriceAlarm
) {

    val mockData1 = StockPriceResponseOutput(
        stck_hgpr = "10000",
        prdy_vrss = "100", // 상승
        prdy_vrss_sign = "2",
        prdy_ctrt = "15",
        stck_oprc = "9000",
        stck_prpr = "11000",
        stck_lwpr = "9500",
        stck_mxpr = "18000",
        stck_llam = "7000"
    )

    val mockData2 = StockPriceResponseOutput(
        stck_hgpr = "18000",
        prdy_vrss = "8000", // 상
        prdy_vrss_sign = "1",
        prdy_ctrt = "15",
        stck_oprc = "9000",
        stck_prpr = "11000",
        stck_lwpr = "9500",
        stck_mxpr = "18000",
        stck_llam = "7000"
    )

    val mockData3 = StockPriceResponseOutput(
        stck_hgpr = "10000",
        prdy_vrss = "100", // 상승
        prdy_vrss_sign = "2",
        prdy_ctrt = "15",
        stck_oprc = "9000",
        stck_prpr = "11000",
        stck_lwpr = "9500",
        stck_mxpr = "18000",
        stck_llam = "7000"
    )

    val mockData4 = StockPriceResponseOutput(
        stck_hgpr = "10000",
        prdy_vrss = "100", // ㅏ하락
        prdy_vrss_sign = "5",
        prdy_ctrt = "15",
        stck_oprc = "9000",
        stck_prpr = "11000",
        stck_lwpr = "9500",
        stck_mxpr = "18000",
        stck_llam = "7000"
    )

    val mockData5 = StockPriceResponseOutput(
        stck_hgpr = "10000",
        prdy_vrss = "100", // 하
        prdy_vrss_sign = "2",
        prdy_ctrt = "15",
        stck_oprc = "9000",
        stck_prpr = "11000",
        stck_lwpr = "9500",
        stck_mxpr = "18000",
        stck_llam = "7000"
    )

    @Test
    fun start() {
        alarm.push(mockData1)
    }

    @Test
    fun end() {
        alarm.stopObserving()
    }

    @Test
    fun touchMaxPrice() {
        alarm.push(mockData1)
        alarm.push(mockData2)
    }

    @Test
    fun breakMaxPrice() {
        alarm.push(mockData1)
        alarm.push(mockData2)
        alarm.push(mockData3)
    }

    @Test
    fun changeSign() {
        alarm.push(mockData1)
        alarm.push(mockData3)
        alarm.push(mockData4)
    }


}
