package com.hms.so.domain.settings

import com.hms.so.common.ResponseObject
import com.hms.so.common.logger
import org.slf4j.Logger
import org.springframework.web.bind.annotation.*

const val name = "Setting Controller"

@RestController
@RequestMapping("setting")
@CrossOrigin("*")
class SettingController(
    private val service: SettingService
) {
    val log: Logger = logger()

    @GetMapping("get")
    fun getSettings(): ResponseObject<SettingDto> {
        log.info(name, "fun:getSettings:Called")
        return try {
            log.info(name, "fun:getSettings:response:Success")
            ResponseObject(
                success = true,
                body = service.getSettings()
            )
        } catch (e: Error) {
            log.error(name, "fun:getSettings:response:Failed:${e.message}")
            ResponseObject(
                success = false,
                errorMessage = e.message
            )
        }
    }

    @PostMapping("save")
    fun saveSettings(
        @RequestBody param: SettingDto
    ): ResponseObject<SettingDto> {
        return try {
            service.insert(param)
            ResponseObject(
                success = true,
                body = service.getSettings()
            )
        } catch (e: Error) {
            ResponseObject(
                success = false,
                errorMessage = e.message
            )
        }

    }

    @PutMapping("update")
    fun updateSettings(
        @RequestBody param: SettingDto
    ): ResponseObject<SettingDto> {
        return try {
            service.update(param)
            ResponseObject(
                success = true,
                body = service.getSettings()
            )
        } catch (e: Error) {
            ResponseObject(
                success = false,
                errorMessage = e.message
            )
        }

    }

    @PutMapping("get_access")
    fun getAccessToken(
        @RequestBody param: SettingDto
    ): ResponseObject<SettingDto> {
        return try {
            ResponseObject(
                success = true,
                body = service.updateAccessToken(param)
            )
        } catch (e: IllegalArgumentException) {
            ResponseObject(
                success = false,
                errorMessage = e.message
            )
        }
    }

    @DeleteMapping("delete")
    fun deleteSettings(): ResponseObject<String> {
        return try {
            service.delete()
            ResponseObject(
                success = true,
            )
        } catch (e: Error) {
            ResponseObject(
                success = false,
                errorMessage = e.message
            )
        }
    }
}
