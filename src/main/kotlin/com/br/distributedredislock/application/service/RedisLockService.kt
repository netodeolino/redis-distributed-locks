package com.br.distributedredislock.application.service

import org.slf4j.LoggerFactory
import org.springframework.integration.redis.util.RedisLockRegistry
import org.springframework.stereotype.Service
import java.util.concurrent.locks.Lock

@Service
class RedisLockService(private val redisLockRegistry: RedisLockRegistry) {

    companion object {
        private val logger = LoggerFactory.getLogger(RedisLockService::class.simpleName)
    }

    fun lock(lockKey: String) {
        val lock: Lock = obtainLock(lockKey)
        lock.lock()
    }

    fun unlock(lockKey: String) {
        try {
            val lock: Lock = obtainLock(lockKey)
            lock.unlock()
            redisLockRegistry.expireUnusedOlderThan(6000)
        } catch (e: Exception) {
            logger.error("[RedisLockService][unlock] [{}]", lockKey, e)
        }
    }

    private fun obtainLock(lockKey: String): Lock {
        return redisLockRegistry.obtain(lockKey)
    }

}