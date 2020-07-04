package io.mns.authlog.service

import io.mns.authlog.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class UserDetailsServiceImpl(private val repository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String?): User? {
        username?.let {
            val user = repository.findByUsername(it) ?: throw UsernameNotFoundException(username)
            return User(user.username, user.password, listOf())
        }
        throw RuntimeException("Username is not provided.")
    }

}