package com.hms.so.domain.stock.alarm

import com.hms.so.infrastructure.slack.SlackWebHookBody


interface Alarm<T, M> {
    val list: MutableList<T>
    fun push(outputData: T) {
        if (list.size <= 10) {
            list.add(outputData)
        } else {
            list.removeFirst()
            list.add(outputData)
        }

        val message = checkCondition()

        message?.let {
            sendMessage(
                createMessage(list.last(), message)
            )
        }
    }

    fun stopObserving()
    fun checkCondition(): M?
    fun createMessage(outputData: T, message: M): SlackWebHookBody
    fun sendMessage(message: SlackWebHookBody)
}
