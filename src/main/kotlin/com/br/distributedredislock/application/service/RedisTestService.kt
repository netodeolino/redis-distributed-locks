package com.br.distributedredislock.application.service

import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import java.util.*

@Service
class RedisTestService(
        private val redisLockService: RedisLockService,
        private val valueOperations: ValueOperations<String, Any>
) {

    fun set(key: String): Any? {
        valueOperations.set(key, "ABC")
        return valueOperations.get("A")
    }

    fun lock(key: String): String {
        for (i in 0..9) {
            Thread {
                redisLockService.lock(key)

                try {
                    Thread.sleep(3000L)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                System.out.println(Date())

                redisLockService.unlock(key)
            }.start()
        }

        return "OK"
    }

}