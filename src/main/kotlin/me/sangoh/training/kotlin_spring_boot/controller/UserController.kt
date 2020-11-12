package me.sangoh.training.kotlin_spring_boot.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import me.sangoh.training.kotlin_spring_boot.entity.User
import me.sangoh.training.kotlin_spring_boot.repo.UserJpaRepo
import org.springframework.web.bind.annotation.*

@Api(tags = ["1. User"])
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = ["/v1"])
class UserController(private val userJpaRepo: UserJpaRepo) {

    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다.")
    @GetMapping(value = ["/user"])
    fun findAllUser(): List<User?> {
        return userJpaRepo.findAll().toList()
    }

    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping(value = ["/user"])
    fun save(
            @ApiParam(value = "회원 아이디", required = true) @RequestParam uid: String,
            @ApiParam(value = "회원 이름", required = true) @RequestParam name: String
    ): User {
        val user = User(
                uid = uid,
                name = name)

        return userJpaRepo.save(user)
    }
}
