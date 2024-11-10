package com.hms.so.domain.settings

import com.hms.so.configuration.ResponseObject
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("setting")
@CrossOrigin("*")
class SettingController(
    private val service: SettingService
) {

    @GetMapping("get")
    fun getSettings(): ResponseObject<SettingDto> {
        return try {
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
