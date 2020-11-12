package me.sangoh.training.kotlin_spring_boot.model.response

import io.swagger.annotations.ApiModelProperty

open class CommonResult(
        @ApiModelProperty(value = "응답 여부 성공 : true/false")
        var success: Boolean,

        @ApiModelProperty(value = "응답 코드 번호 : >= 0 정상, < 비정상")
        var code: Int,

        @ApiModelProperty(value = "응답 메시지")
        var msg: String
)