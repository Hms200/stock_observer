package com.hms.so.settings

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("setting")
class SettingController(
    private val service: SettingService
) {

    @GetMapping("get")
    fun getSettings(): SettingDto? {
        return service.getSettings()
    }

    @PostMapping("save")
    fun saveSettings(
        @RequestBody param: SettingDto
    ): SettingDto? {
        service.insert(param)
        return service.getSettings()
    }

    @PutMapping("update")
    fun updateSettings(
        @RequestBody param: SettingDto
    ): SettingDto? {
        service.update(param)
        return service.getSettings()
    }

    @DeleteMapping("delete")
    fun deleteSettings(): SettingDto? {
        service.delete()
        return service.getSettings()
    }
}
