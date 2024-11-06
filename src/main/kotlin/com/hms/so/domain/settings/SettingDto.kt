package com.hms.so.domain.settings

data class SettingDto(
    val id: Long? = null,
    val appKey: String,
    val appSecret: String,
    val webHookUrl: String,
    val accessToken: String? = null,
)
