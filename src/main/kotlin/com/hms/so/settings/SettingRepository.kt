package com.hms.so.settings

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SettingRepository : JpaRepository<Setting, Long> {
}
