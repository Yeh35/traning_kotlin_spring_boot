package me.sangoh.training.kotlin_spring_boot.advice.exception

class CUserNotFoundException : RuntimeException {
    constructor(msg: String?, t: Throwable?) : super(msg, t)
    constructor(msg: String?) : super(msg)
    constructor() : super()
}