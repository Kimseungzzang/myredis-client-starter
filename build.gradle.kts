plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("maven-publish")
}

group = "com.example"
version = "1.0.4"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-autoconfigure:3.4.0")
    compileOnly("org.springframework.boot:spring-boot-configuration-processor:3.4.0")
implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
}

kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Kimseungzzang/myredis-client-starter")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
