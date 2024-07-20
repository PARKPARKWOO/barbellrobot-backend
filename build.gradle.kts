import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.9.10"
    kotlin("jvm") version kotlinVersion apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.3"
    kotlin("kapt") version kotlinVersion apply false
    java
}

allprojects {
    repositories {
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }
    afterEvaluate {
        detekt {
            buildUponDefaultConfig = true
            config.setFrom(files("$rootDir/detekt-config.yml"))
        }
    }
}

subprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint("com.pinterest.ktlint:ktlint-cli:1.0.1") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val ktlintCheck by tasks.registering(JavaExec::class) {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Check Kotlin code style"
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args(
        "**/src/**/*.kt",
        "**.kts",
        "!**/build/**",
    )
}

tasks.register<JavaExec>("ktlintFormat") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Check Kotlin code style and format"
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
    args(
        "-F",
        "**/src/**/*.kt",
        "**.kts",
        "!**/build/**",
    )
}
