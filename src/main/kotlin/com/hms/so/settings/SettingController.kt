package com.hms.so.settings

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("setting")
@CrossOrigin("*")
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
