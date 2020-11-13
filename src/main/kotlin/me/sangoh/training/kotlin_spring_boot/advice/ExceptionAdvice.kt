package me.sangoh.training.kotlin_spring_boot.advice

import me.sangoh.training.kotlin_spring_boot.advice.exception.CUserNotFoundException
import me.sangoh.training.kotlin_spring_boot.model.response.CommonResult
import me.sangoh.training.kotlin_spring_boot.service.ResponseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionAdvice(
        private val responseService: ResponseService
) {

//    @ExceptionHandler(Exception::class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected fun defaultException(request: HttpServletRequest, e: Exception): CommonResult {
//        return responseService.getFailResult()
//    }

    @ExceptionHandler(CUserNotFoundException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun userNotFoundException(request: HttpServletRequest, e: CUserNotFoundException): CommonResult {
        return responseService.getFailResult()
    }

}