package me.sangoh.training.kotlin_spring_boot.controller

import me.sangoh.training.kotlin_spring_boot.entity.User
import me.sangoh.training.kotlin_spring_boot.repo.UserJpaRepo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = ["/v1"])
class UserController(private val userJpaRepo: UserJpaRepo) {

    @GetMapping(value = ["/user"])
    fun findAllUser(): List<User?> {
        return userJpaRepo.findAll().toList()
    }

    @PostMapping(value = ["/user"])
    fun save(): User {
        var user = User(
                name = "유미",
                uid = "yumi@naver.com")

        user = userJpaRepo.save(user)
        return user
    }
}
