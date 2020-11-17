package me.sangoh.training.kotlin_spring_boot.repo

import me.sangoh.training.kotlin_spring_boot.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserJpaRepo : JpaRepository<User?, Long?> {
    fun findByUid(email: String): Optional<User>
}