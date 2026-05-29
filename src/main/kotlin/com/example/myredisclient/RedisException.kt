package com.example.myredisclient

open class RedisException(message: String) : RuntimeException(message)

class RedisWrongTypeException(message: String) : RedisException(message)
