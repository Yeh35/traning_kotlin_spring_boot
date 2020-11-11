package me.sangoh.training.kotlin_spring_boot.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloController {

    @GetMapping(value = ["/helloworld/string"])
    @ResponseBody
    fun helloworldString(): String {
        return "hello world"
    }

    @GetMapping(value = ["/helloworld/json"])
    @ResponseBody
    fun helloworldJson(): Hello {
        return Hello("hello world")
    }

    @GetMapping(value = ["/hellowerld/page"])
    fun helloworldPage(): String {
        return "helloworld"
    }

    data class Hello(
        val message: String
    )

}