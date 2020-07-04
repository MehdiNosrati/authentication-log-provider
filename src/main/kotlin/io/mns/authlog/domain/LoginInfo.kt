package io.mns.authlog.domain

import javax.persistence.*

@Entity
@Table(name = "authentication_logs")
data class LoginInfo(@Column(name = "timestamp") val timestamp: Long,
                     @Column(name = "username") val username: String,
                     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
                     var id: Long = 0
)