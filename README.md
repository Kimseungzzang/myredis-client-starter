# myredis-client-starter

A Spring Boot auto-configuration starter for [MyRedis](https://github.com/Kimseungzzang/ticketing/tree/main/MyRedis) — a lightweight custom Redis server built with Netty and the RESP protocol.

## Requirements

- Java 21+
- Spring Boot 3.x+

## Installation

### 1. Add GitHub Packages repository to `build.gradle.kts`

```kotlin
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/Kimseungzzang/myredis-client-starter")
        credentials {
            username = System.getenv("GITHUB_ACTOR") ?: project.findProperty("gpr.user") as String?
            password = System.getenv("GITHUB_TOKEN") ?: project.findProperty("gpr.token") as String?
        }
    }
}
```

### 2. Add dependency

```kotlin
dependencies {
    implementation("com.example:myredis-client-starter:1.0.2")
}
```

### 3. Set up credentials

GitHub Packages requires authentication even for public packages.

**Local development** — add to `~/.gradle/gradle.properties`:
```properties
gpr.user=YOUR_GITHUB_USERNAME
gpr.token=YOUR_GITHUB_PAT  # read:packages scope required
```

**GitHub Actions** — `GITHUB_TOKEN` is injected automatically, no extra setup needed.

## Configuration

Add the following to `application.yml`:

```yaml
myredis:
  host: localhost       # default: localhost
  port: 6379            # default: 6379
  pool-size: 4          # default: 4
  connect-timeout-ms: 1000  # default: 1000
  read-timeout-ms: 1000     # default: 1000
```

## Usage

`MyRedisTemplate` is automatically registered as a Spring bean. Just inject it:

```kotlin
@Service
class MyService(private val myRedisTemplate: MyRedisTemplate) {

    fun example() {
        // String operations
        myRedisTemplate.setKey("key", "value", ttlSec = 300)
        val value = myRedisTemplate.getKey("key")
        myRedisTemplate.delKey("key")

        // Counter operations
        myRedisTemplate.incrKey("counter")
        myRedisTemplate.decrKey("counter")

        // Sorted Set operations
        myRedisTemplate.zadd("queue", score = 1000L, member = "user1")
        val rank = myRedisTemplate.zrank("queue", "user1")
        val total = myRedisTemplate.zcard("queue")
        val popped = myRedisTemplate.zpopmin("queue", count = 2)
    }
}
```

## Available Methods

### String

| Method | Description |
|---|---|
| `getKey(key)` | Get value by key |
| `setKey(key, value, ttlSec)` | Set value with TTL in seconds (`-1` = no TTL) |
| `delKey(vararg keys)` | Delete one or more keys |
| `incrKey(key)` | Atomically increment integer value |
| `decrKey(key)` | Atomically decrement integer value |
| `ttlSec(key)` | Get remaining TTL in seconds |
| `keysAll()` | Get all keys |
| `type(key)` | Get key type (`string`, `zset`, `none`) |

### Sorted Set

| Method | Description |
|---|---|
| `zadd(key, score, member)` | Add member with score |
| `zrank(key, member)` | Get 0-indexed rank (ascending) |
| `zcard(key)` | Get total member count |
| `zpopmin(key, count)` | Pop lowest-scored members |
| `zrangeWithScores(key)` | Get all members with scores (ascending) |

## Customization

To override the default auto-configuration, define your own bean:

```kotlin
@Configuration
class MyRedisConfig {
    @Bean
    fun myRedisTemplate(): MyRedisTemplate = MyRedisTemplate(
        host = "custom-host",
        port = 6380,
        poolSize = 10,
    )
}
```

## License

MIT
