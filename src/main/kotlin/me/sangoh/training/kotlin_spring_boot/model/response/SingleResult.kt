package me.sangoh.training.kotlin_spring_boot.model.response

open class SingleResult<T>(var data: T, success: Boolean, code: Int, msg: String) : CommonResult(success, code, msg)