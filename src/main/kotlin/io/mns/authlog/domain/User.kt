package io.mns.authlog.domain

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(@Column(name = "username") val username: String,
                @Column(name = "password") var password: String,
                @Column(name = "name") val name: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0L

}
