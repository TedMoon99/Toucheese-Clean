plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    testImplementation(libs.junit) // JUnit 4
    testImplementation(libs.kotlinx.coroutines.test) // 코루틴 테스트
    testImplementation(libs.mockk) // Mocking 라이브러리
    testImplementation(libs.turbine) // Flow 테스트 도구
}