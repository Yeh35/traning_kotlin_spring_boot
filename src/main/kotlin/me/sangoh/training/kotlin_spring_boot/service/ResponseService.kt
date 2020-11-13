package me.sangoh.training.kotlin_spring_boot.service

import me.sangoh.training.kotlin_spring_boot.model.response.CommonResult
import me.sangoh.training.kotlin_spring_boot.model.response.ListResult
import me.sangoh.training.kotlin_spring_boot.model.response.SingleResult
import org.springframework.stereotype.Service

@Service
class ResponseService {

    enum class CommonResponse(val code: Int, val msg: String) {
        SUCCESS(0, "성공하였습니다."),
        FAIL(-1, "실패하였습니다.")
    }

    fun <T> getSingleResult(data: T): SingleResult<T> {

        return SingleResult(
                data = data,
                code = CommonResponse.SUCCESS.code,
                msg = CommonResponse.SUCCESS.msg,
                success = true
        )
    }

    fun <E> getListResult(list: List<E>): ListResult<E> {

        return ListResult(
                list = list,
                code = CommonResponse.SUCCESS.code,
                msg = CommonResponse.SUCCESS.msg,
                success = true
        )
    }

    fun getSuccessResult(): CommonResult {

        return CommonResult(
                code = CommonResponse.SUCCESS.code,
                msg = CommonResponse.SUCCESS.msg,
                success = true
        )
    }

    fun getFailResult(code: Int, msg: String): CommonResult {

        return CommonResult(
                code = code,
                msg = msg,
                success = false
        )
    }

}