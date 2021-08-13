package com.br.distributedredislock.adapter.`in`.controller

import com.br.distributedredislock.application.service.RedisTestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("test")
class TestController(private val redisTestService: RedisTestService) {

    @GetMapping("set")
    fun set(@RequestParam key: String): Any? {
        return redisTestService.set(key)
    }

    @GetMapping("lock")
    fun lock(@RequestParam key: String): String {
        return redisTestService.lock(key)
    }

}