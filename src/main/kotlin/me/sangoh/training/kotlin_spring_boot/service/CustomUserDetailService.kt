package me.sangoh.training.kotlin_spring_boot.service

import me.sangoh.training.kotlin_spring_boot.advice.exception.CUserNotFoundException
import me.sangoh.training.kotlin_spring_boot.repo.UserJpaRepo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService(
        private val userJpaRepo: UserJpaRepo
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        return userJpaRepo.findById(username.toLong()).orElseThrow {
            CUserNotFoundException()
        }
    }
}