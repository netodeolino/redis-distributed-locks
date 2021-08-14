package com.br.distributedredislock.application.port.`in`

interface RedisTestUseCase {

    fun set(key: String, value: String): Any?
    fun lock(key: String): String

}