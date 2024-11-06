package com.hms.so.setting

import com.hms.so.domain.settings.Setting
import com.hms.so.domain.settings.SettingRepository
import com.hms.so.domain.settings.toDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class SettingTest(
    @Autowired val repository: SettingRepository
) {

    val appKey: String = "app_key"
    val appSecret: String = "app_secret"
    val webHookUrl: String = "web.hook.url"

    @Test
    fun entityCreateTest() {
        val result = repository.findAll().size

        Assertions.assertEquals(result, 0)
    }

    @Test
    fun insertTest() {
        val result = repository.save(
            Setting(
                appKey = appKey,
                appSecret = appSecret,
                webHookUrl = webHookUrl,
            )
        )

        Assertions.assertEquals(result.appKey, appKey)
    }

    @Test
    fun updateTest() {

        val entity = repository.save(
            Setting(
                appKey = appKey,
                appSecret = appSecret,
                webHookUrl = webHookUrl
            )
        )

        val dto = entity.toDto()

        checkNotNull(dto.id) { "id must not be null" }

        val target = repository.findById(dto.id!!)

        if (target.isEmpty) {
            throw IllegalArgumentException()
        } else {
            val result = repository.save(
                Setting(
                    id = dto.id,
                    appKey = "new Key",
                    appSecret = dto.appSecret,
                    webHookUrl = dto.webHookUrl,
                )
            )

            Assertions.assertEquals(result.appKey, "new Key")
        }
    }
}
