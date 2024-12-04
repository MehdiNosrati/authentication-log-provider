package io.mns.authlog.controller

import io.mns.authlog.domain.LoginInfo
import io.mns.authlog.repository.LogRepository
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LogController(private val logRepository: LogRepository) {

    @GetMapping("/logs")
    fun logs(authentication: Authentication): List<LoginInfo>? {
        return logRepository.findByUsername(authentication.name)?.reversed()
    }

    @DeleteMapping("/logs/{id}")
    fun deleteLog(@PathVariable("id") id: Long, authentication: Authentication) {
        val logInfo = logRepository.findByUsername(authentication.name)?.findLast { it.id == id }
        logInfo?.let {
            logRepository.delete(it)
        }
    }
}