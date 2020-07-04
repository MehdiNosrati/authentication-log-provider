package io.mns.authlog.controller

import io.mns.authlog.domain.User
import io.mns.authlog.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val repository: UserRepository, private val passwordEncoder: BCryptPasswordEncoder) {

    @PostMapping("sign-up")
    fun signUp(@RequestBody user: User) {
        user.password = passwordEncoder.encode(user.password)
        repository.save(user)
    }
}