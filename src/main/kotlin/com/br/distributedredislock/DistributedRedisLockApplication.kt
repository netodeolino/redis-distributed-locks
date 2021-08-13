package com.br.distributedredislock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DistributedRedisLockApplication

fun main(args: Array<String>) {
	runApplication<DistributedRedisLockApplication>(*args)
}
