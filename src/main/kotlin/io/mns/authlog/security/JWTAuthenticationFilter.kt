package io.mns.authlog.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import io.mns.authlog.SecurityConstants.EXPIRATION_TIME
import io.mns.authlog.SecurityConstants.EXPOSE_HEADERS
import io.mns.authlog.SecurityConstants.HEADER_STRING
import io.mns.authlog.SecurityConstants.SECRET
import io.mns.authlog.SecurityConstants.TOKEN_PREFIX
import io.mns.authlog.repository.LogRepository
import io.mns.authlog.utility.LogManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(private val authManager: AuthenticationManager,
                              private val logRepository: LogRepository) :
        UsernamePasswordAuthenticationFilter() {
    private val logManager: LogManager by lazy {
        LogManager(logRepository)
    }
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        try {
            val user = ObjectMapper().readValue(request?.inputStream, io.mns.authlog.domain.User::class.java)
            return authManager.authenticate(UsernamePasswordAuthenticationToken(
                    user.username,
                    user.password,
                    listOf()
            ))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        val username = (authResult?.principal as User).username
        val token = JWT.create()
                .withSubject(username)
                .withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET))
        response?.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
        response?.addHeader(EXPOSE_HEADERS, HEADER_STRING)
        logManager.addSuccessfulLogin(username)
    }
}