package com.br.distributedredislock.application.port.out

interface RedisLockHelperPort {

    fun lock(lockKey: String)
    fun unlock(lockKey: String)

}