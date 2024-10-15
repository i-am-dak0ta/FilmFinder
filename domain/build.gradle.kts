plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {

    // Coroutines
    implementation(libs.jetbrains.kotlinx.coroutines.core)

    // Javax Inject
    implementation(libs.javax.inject)

    // Tests
    testImplementation(libs.junit)
}