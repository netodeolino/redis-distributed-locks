package com.br.distributedredislock.adapter.out.redis

import com.br.distributedredislock.application.port.out.RedisLockHelperPort
import org.slf4j.LoggerFactory
import org.springframework.integration.redis.util.RedisLockRegistry
import org.springframework.stereotype.Service
import java.util.concurrent.locks.Lock

@Service
class RedisLockHelper(private val redisLockRegistry: RedisLockRegistry) : RedisLockHelperPort {

    companion object {
        private val logger = LoggerFactory.getLogger(RedisLockHelper::class.simpleName)
    }

    override fun lock(lockKey: String) {
        val lock: Lock = obtainLock(lockKey)
        lock.lock()
    }

    override fun unlock(lockKey: String) {
        try {
            val lock: Lock = obtainLock(lockKey)
            lock.unlock()
        } catch (e: Exception) {
            logger.error("[RedisLockService][unlock] [{}]", lockKey, e)
        }
    }

    private fun obtainLock(lockKey: String): Lock {
        return redisLockRegistry.obtain(lockKey)
    }

}