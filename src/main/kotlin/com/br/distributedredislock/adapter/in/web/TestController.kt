package com.br.distributedredislock.adapter.`in`.web

import com.br.distributedredislock.application.port.`in`.RedisTestUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class TestController(private val redisTestUseCase: RedisTestUseCase) {

    @GetMapping("set")
    fun set(@RequestParam key: String, @RequestParam value: String): Any? {
        return redisTestUseCase.set(key, value)
    }

    @GetMapping("lock")
    fun lock(@RequestParam key: String): String {
        return redisTestUseCase.lock(key)
    }

}
