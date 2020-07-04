package io.mns.authlog.utility

import io.mns.authlog.MAX_LOG_COUNT
import io.mns.authlog.domain.LoginInfo
import io.mns.authlog.repository.LogRepository
import java.util.*

class LogManager(private val logRepository: LogRepository) {
    fun addSuccessfulLogin(username: String) {
        logRepository.findByUsername(username)?.let {
            if (it.size < MAX_LOG_COUNT) {
                logRepository.save(LoginInfo(Date().time, username))
            }
            else {
                val id = it.stream().min(compareBy(LoginInfo::timestamp)).get().id
                logRepository.save(LoginInfo(Date().time, username, id))
            }
        }
    }
}