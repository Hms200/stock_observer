package com.hms.so.domain.settings

import com.hms.so.infrastructure.hantoo.AccessTokenService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SettingService(
    private val repository: SettingRepository,
    private val accessTokenService: AccessTokenService
) {

    fun getSettings(): SettingDto? {
        val result = repository.findAll()

        return if (result.size > 0) {
            result.last().toDto()
        } else {
            null
        }
    }

    fun insert(dto: SettingDto) {
        repository.save(
            Setting(
                appKey = dto.appKey,
                appSecret = dto.appSecret,
                webHookUrl = dto.webHookUrl
            )
        )
    }

    fun update(dto: SettingDto) {
        checkNotNull(dto.id) { "id must not be null" }
        val target = repository.findById(dto.id)

        if (target.isEmpty) {
            throw IllegalArgumentException()
        } else {
            repository.save(
                Setting(
                    id = dto.id,
                    appKey = dto.appKey,
                    appSecret = dto.appSecret,
                    webHookUrl = dto.webHookUrl,
                )
            )
        }
    }

    fun updateAccessToken(settingDto: SettingDto): SettingDto {
        checkNotNull(settingDto.appKey)
        val response = accessTokenService.getAccessToken(settingDto)
        if (response?.access_token.isNullOrBlank()) {
            throw IllegalArgumentException("appkey appsecret 확인")
        }
        return repository.save(
            Setting(
                id = settingDto.id,
                appKey = settingDto.appKey,
                appSecret = settingDto.appSecret,
                webHookUrl = settingDto.webHookUrl,
                accessToken = response?.access_token,
            )
        ).toDto()

    }

    fun delete() {
        repository.deleteAll()
    }

}
