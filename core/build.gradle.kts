import org.jetbrains.kotlin.gradle.idea.proto.com.google.protobuf.GeneratedCodeInfoKt.annotation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // mongoDB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.redisson:redisson:3.25.2")

    // test
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.8.0")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.8.0")
    testImplementation("io.mockk:mockk:1.13.8")

    // query dsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
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

tasks.bootJar {
    enabled = false
    mainClass = "com.example.api.ApiApplication"
}

kapt {
    arguments {
        arg("querydsl.sourceDir", "${project.layout.buildDirectory}/generated/querydsl")
    }
}