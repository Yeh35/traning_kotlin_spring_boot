package me.sangoh.training.kotlin_spring_boot.advice.exception

import java.lang.RuntimeException


class CAuthenticationEntryPointException : RuntimeException {

    constructor(): super()
    constructor(msg:String): super(msg)
    constructor(msg: String, t: Throwable): super(msg, t)

}
