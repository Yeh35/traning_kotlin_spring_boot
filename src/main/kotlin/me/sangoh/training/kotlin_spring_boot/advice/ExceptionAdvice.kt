package me.sangoh.training.kotlin_spring_boot.advice

import me.sangoh.training.kotlin_spring_boot.advice.exception.CAuthenticationEntryPointException
import me.sangoh.training.kotlin_spring_boot.advice.exception.CEmailSigninFailedException
import me.sangoh.training.kotlin_spring_boot.advice.exception.CUserNotFoundException
import me.sangoh.training.kotlin_spring_boot.model.response.CommonResult
import org.springframework.security.access.AccessDeniedException
import me.sangoh.training.kotlin_spring_boot.service.ResponseService
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest


@RestControllerAdvice
class ExceptionAdvice(
        private val responseService: ResponseService,
        private val messageSource: MessageSource
) {
    @ExceptionHandler(CUserNotFoundException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun userNotFoundException(request: HttpServletRequest, e: CUserNotFoundException): CommonResult {
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"))
    }

    @ExceptionHandler(CEmailSigninFailedException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun emailSigninFailed(request: HttpServletRequest, e: CEmailSigninFailedException): CommonResult {
        return responseService.getFailResult(getMessage("emailSigninFailed.code").toInt(), getMessage("emailSigninFailed.msg"))
    }

    @ExceptionHandler(CAuthenticationEntryPointException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun authenticationEntryPointException(request: HttpServletRequest, e: CAuthenticationEntryPointException): CommonResult {
        return responseService.getFailResult(getMessage("entryPointException.code").toInt(), getMessage("entryPointException.msg"))
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedException(request: HttpServletRequest?, e: AccessDeniedException?): CommonResult? {
        return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"))
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun defaultException(request: HttpServletRequest, e: Exception): CommonResult {
        e.printStackTrace()
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"))
    }


    private fun getMessage(code: String): String {
        return getMessage(code, null)
    }

    private fun getMessage(code: String, args: Array<Any>?): String {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
    }

}