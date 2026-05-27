package com.example.myredisclient

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean

@AutoConfiguration
@EnableConfigurationProperties(MyRedisProperties::class)
class MyRedisAutoConfiguration(private val props: MyRedisProperties) {

    @Bean
    @ConditionalOnMissingBean
    fun redisTokenStore(): RedisTokenStore = RedisTokenStore(
        host = props.host,
        port = props.port,
        connectTimeoutMs = props.connectTimeoutMs,
        readTimeoutMs = props.readTimeoutMs,
        poolSize = props.poolSize,
    )
}
