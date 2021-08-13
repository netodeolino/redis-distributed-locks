package com.br.distributedredislock.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.integration.redis.util.RedisLockRegistry

@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory?): RedisTemplate<String, Any>? {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(factory!!)
        val stringSerializer = StringRedisSerializer()
        val jacksonSerializer = getJacksonSerializer()
        template.keySerializer = stringSerializer
        template.valueSerializer = jacksonSerializer
        template.hashKeySerializer = stringSerializer
        template.hashValueSerializer = jacksonSerializer
        template.setEnableTransactionSupport(true)
        template.afterPropertiesSet()
        return template
    }

    private fun getJacksonSerializer(): RedisSerializer<*> {
        val om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL)
        return GenericJackson2JsonRedisSerializer(om)
    }

    @Bean
    fun valueOperations(redisTemplate: RedisTemplate<String?, Any?>): ValueOperations<String?, Any?>? {
        return redisTemplate.opsForValue()
    }

    @Bean(destroyMethod = "destroy")
    fun redisLockRegistry(redisConnectionFactory: RedisConnectionFactory?): RedisLockRegistry? {
        return RedisLockRegistry(redisConnectionFactory, "lock")
    }

}