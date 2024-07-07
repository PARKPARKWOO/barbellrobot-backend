import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
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

//tasks.bootJar {
//    enabled = false
//    mainClass = "com.example.api.ApiApplication"
//}
