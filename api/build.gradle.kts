plugins {
    val kotlinVersion = "1.9.10"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("kapt") version kotlinVersion apply false
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":application"))
    implementation(project(":infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // swagger-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // log
    implementation("net.logstash.logback:logstash-logback-encoder:6.6")

    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("com.amazonaws:aws-java-sdk-s3:1.12.666")

    // valid
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // encryption
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

    // rate limiter
    implementation("com.bucket4j:bucket4j-core:8.10.1")

    // gemini auto-config
//    implementation("org.springframework.ai:spring-ai-vertex-ai-gemini-spring-boot-starter")

    // log-loki
    implementation("com.github.loki4j:loki-logback-appender:1.5.1")
}

extra["springAiVersion"] = "1.0.0-SNAPSHOT"
extra["springCloudVersion"] = "2023.0.0"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}
