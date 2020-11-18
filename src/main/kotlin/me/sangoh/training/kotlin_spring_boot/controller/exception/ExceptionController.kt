package me.sangoh.training.kotlin_spring_boot.controller.exception

import me.sangoh.training.kotlin_spring_boot.advice.exception.CAuthenticationEntryPointException
import me.sangoh.training.kotlin_spring_boot.model.response.CommonResult
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping(value = ["/exception"])
@RestController
class ExceptionController {

    @GetMapping(value = ["/entrypoint"])
    fun entrypointException() {
        throw CAuthenticationEntryPointException()
    }

    @GetMapping(value = ["/accessdenied"])
    fun accessdeniedException(): CommonResult? {
        throw AccessDeniedException("")
    }
}