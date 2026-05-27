package com.example.myredisclient

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "myredis")
data class MyRedisProperties(
    val host: String = "localhost",
    val port: Int = 6379,
    val connectTimeoutMs: Int = 1000,
    val readTimeoutMs: Int = 1000,
    val poolSize: Int = 4,
)
