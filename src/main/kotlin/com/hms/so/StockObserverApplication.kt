package com.hms.so

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockObserverApplication

fun main(args: Array<String>) {
    runApplication<StockObserverApplication>(*args)
}
