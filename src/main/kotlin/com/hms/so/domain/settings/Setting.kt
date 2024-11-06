package com.hms.so.domain.settings

import jakarta.persistence.*

@Entity
class Setting(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column
    var appKey: String,

    @Column
    var appSecret: String,

    @Column
    var webHookUrl: String,

    @Column
    var accessToken: String? = null,
)

fun Setting.toDto(): SettingDto {
    return SettingDto(
        id = this.id,
        appKey = this.appKey,
        appSecret = this.appSecret,
        webHookUrl = this.webHookUrl,
        accessToken = this.accessToken,
    )
}
