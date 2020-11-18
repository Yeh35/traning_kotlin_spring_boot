package me.sangoh.training.kotlin_spring_boot.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import me.sangoh.training.kotlin_spring_boot.advice.exception.CEmailSigninFailedException
import me.sangoh.training.kotlin_spring_boot.entity.User
import me.sangoh.training.kotlin_spring_boot.model.response.CommonResult
import me.sangoh.training.kotlin_spring_boot.model.response.SingleResult
import me.sangoh.training.kotlin_spring_boot.repo.UserJpaRepo
import me.sangoh.training.kotlin_spring_boot.security.JwtTokenProvider
import me.sangoh.training.kotlin_spring_boot.service.ResponseService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Api(tags = ["1. Sign"])
@RestController
@RequestMapping(value = ["/v1"])
class SignController(
        private val userJpaRepo: UserJpaRepo,
        private val jwtTokenProvider: JwtTokenProvider,
        private val responseService: ResponseService,
        private val passwordEncoder: PasswordEncoder
) {


    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping(value = ["/signin"])
    fun signin(
            @ApiParam(value = "회원ID : 이메일", required = true) @RequestParam id: String,
            @ApiParam(value = "비밀번호", required = true) @RequestParam password: String
    ): SingleResult<String> {
        val user = userJpaRepo.findByUid(id).orElseThrow { CEmailSigninFailedException() }
        if (!passwordEncoder.matches(password, user.password)) {
            throw CEmailSigninFailedException()
        }

        return responseService.getSingleResult(jwtTokenProvider.createToken(user.msrl.toString(), user.roles))
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = ["/signup"])
    fun signup(
            @RequestParam id: String,
            @RequestParam password: String,
            @RequestParam name: String
    ): CommonResult {
        userJpaRepo.save(User(
                uid = id,
                password = passwordEncoder.encode(password),
                name = name,
                roles = Collections.singletonList("ROLE_USER")
        ))

        return responseService.getSuccessResult()
    }
}