package me.sangoh.training.kotlin_spring_boot.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import me.sangoh.training.kotlin_spring_boot.advice.exception.CUserNotFoundException
import me.sangoh.training.kotlin_spring_boot.entity.User
import me.sangoh.training.kotlin_spring_boot.model.response.CommonResult
import me.sangoh.training.kotlin_spring_boot.model.response.ListResult
import me.sangoh.training.kotlin_spring_boot.model.response.SingleResult
import me.sangoh.training.kotlin_spring_boot.repo.UserJpaRepo
import me.sangoh.training.kotlin_spring_boot.service.ResponseService
import org.springframework.web.bind.annotation.*

@Api(tags = ["1. User"])
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = ["/v1"])
class UserController(
        private val userJpaRepo: UserJpaRepo,
        private val responseService: ResponseService
) {

    @ApiOperation(value = "회원 조회", notes = "모든 회원을 조회한다.")
    @GetMapping(value = ["/user"])
    fun findAllUser(): ListResult<User?> {
        return responseService.getListResult(userJpaRepo.findAll())
    }

    @ApiOperation(value = "회원 조회", notes = "userId로 회원을 조회한다.")
    @GetMapping(value = ["/user/{msrl}"])
    fun findUserById(@ApiParam(value = "회원 ID", required = true) @PathVariable msrl: Long): SingleResult<User?> {
        return responseService.getSingleResult(userJpaRepo.findById(msrl).orElseThrow {CUserNotFoundException()})
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

    @ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
    @PutMapping(value = ["/user"])
    fun modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam msrl: Long,
            @ApiParam(value = "회원아이디", required = true) @RequestParam uid: String,
            @ApiParam(value = "회원이름", required = true) @RequestParam name: String
    ): SingleResult<User> {

        val user = User(
                msrl = msrl,
                uid = uid,
                name = name)

        return responseService.getSingleResult(userJpaRepo.save(user))
    }

    @ApiOperation(value = "회원 삭제", notes = "userid로 회원정보를 삭제한다")
    @DeleteMapping(value = ["/user/{msrl}"])
    fun delete(
            @ApiParam(value = "회원번호", required = true) @RequestParam msrl: Long
    ): CommonResult {
        userJpaRepo.deleteById(msrl)
        return responseService.getSuccessResult()
    }
}
