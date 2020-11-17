package me.sangoh.training.kotlin_spring_boot.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.stream.Collectors
import javax.persistence.*


@Table(name = "user")
@Entity
class User(
        msrl: Long = 0,
        uid: String,
        password: String,
        name: String,
        roles: List<String> = ArrayList()
): UserDetails {
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        var msrl: Long = 0
                private set

        @Column(nullable = false, unique = true, length = 30)
        var uid: String
                private set

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Column(nullable = false, length = 100)
        private var password: String //UserDetails에 getPassword라는 메소드가 있다.

        @Column(nullable = false, length = 100)
        var name: String
                private set

        @ElementCollection(fetch = FetchType.EAGER)
        val roles: List<String>

        init {
                this.msrl = msrl
                this.uid = uid
                this.name = name
                this.password = password
                this.roles = roles
        }

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                return this.roles.stream()
                        .map { SimpleGrantedAuthority(it) }
                        .collect(Collectors.toList())
        }

        override fun getPassword(): String {
                return password
        }

        override fun getUsername(): String {
                return this.uid
        }

        override fun isAccountNonExpired(): Boolean {
                return true
        }

        override fun isAccountNonLocked(): Boolean {
                return true
        }

        override fun isCredentialsNonExpired(): Boolean {
                return true
        }

        override fun isEnabled(): Boolean {
                return true
        }
}

