package io.mns.authlog.repository

import io.mns.authlog.domain.LoginInfo
import org.springframework.data.jpa.repository.JpaRepository

interface LogRepository : JpaRepository<LoginInfo, Long> {
    fun findByUsername(username: String): List<LoginInfo>?
}