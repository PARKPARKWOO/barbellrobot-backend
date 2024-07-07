import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.10"
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("kapt") version kotlinVersion
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

dependencies {
    implementation(project(":common"))
    implementation(project(":application"))
    implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")
    // mongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.redisson:redisson:3.26.0")
    // test
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.8.0")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
    testImplementation("io.mockk:mockk:1.13.8")

    // query dsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    // oauth
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    // feign
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("io.github.openfeign:feign-httpclient:13.2")

    // s3
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.666")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")

    // uuid generator
    implementation("com.fasterxml.uuid:java-uuid-generator:5.0.0")

    // mail
    implementation("org.springframework.boot:spring-boot-starter-mail:3.2.4")

    // gemini
    implementation("org.springframework.ai:spring-ai-vertex-ai-gemini-spring-boot-starter")

    // multi-part-file mock
    implementation("org.springframework:spring-mock:2.0.8")
}

extra["springAiVersion"] = "1.0.0-SNAPSHOT"
extra["springCloudVersion"] = "2023.0.0"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register("prepareKotlinBuildScriptModel") {}

tasks.bootJar {
    enabled = false
    mainClass = "com.example.api.ApiApplication"
}

kapt {
    arguments {
        arg("querydsl.sourceDir", "${project.layout.buildDirectory}/generated/querydsl")
    }
}
