package com.hms.so.settings

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

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
)

fun Setting.toDto(): SettingDto {
    return SettingDto(
        id = this.id,
        appKey = this.appKey,
        appSecret = this.appSecret,
        webHookUrl = this.webHookUrl
    )
}
