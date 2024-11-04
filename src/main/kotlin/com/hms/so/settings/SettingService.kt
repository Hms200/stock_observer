package com.hms.so.settings

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SettingService(
    private val repository: SettingRepository
) {

    fun getSettings(): SettingDto? {
        val result = repository.findAll()

        return if(result.size > 0) {
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

    fun delete() {
        repository.deleteAll()
    }

}
