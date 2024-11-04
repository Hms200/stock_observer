package com.hms.so.settings

data class SettingDto(
    val id: Long? = null,
    val appKey: String,
    val appSecret: String,
    val webHookUrl: String,
)
