package com.br.distributedredislock.application.service

import com.br.distributedredislock.application.port.`in`.RedisTestUseCase
import com.br.distributedredislock.application.port.out.RedisLockHelperPort
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import java.util.Date

@Service
class RedisTestService(
        private val redisLockHelperPort: RedisLockHelperPort,
        private val valueOperations: ValueOperations<String, Any>
) : RedisTestUseCase {

    override fun set(key: String, value: String): Any? {
        valueOperations.set(key, value)
        return valueOperations.get(key)
    }

    override fun lock(key: String): String {
        for (i in 0..3) {
            Thread {
                println("[Thread][$i] - Get 1: ${valueOperations.get(key)}")

                println("[Thread][$i] - Lock")
                redisLockHelperPort.lock(key)

                println("[Thread][$i] - Get 2: ${valueOperations.get(key)}")

                println("[Thread][$i] - Set 1: ${valueOperations.set(key, i)}")

                println("[Thread][$i] - Get 3: ${valueOperations.get(key)}")

                try {
                    Thread.sleep(4000L)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                println("[Thread][$i] - Date: ${Date()}")

                println("[Thread][$i] - Unlock")
                redisLockHelperPort.unlock(key)
            }.start()
        }

        return "OK"
    }

}
