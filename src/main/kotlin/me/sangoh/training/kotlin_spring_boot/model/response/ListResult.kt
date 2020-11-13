package me.sangoh.training.kotlin_spring_boot.model.response

open class ListResult<T>(var list: List<T>, success: Boolean, code: Int, msg: String) : CommonResult(success, code, msg)