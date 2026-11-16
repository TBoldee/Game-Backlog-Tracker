plugins {
    kotlin("jvm") version "2.4.21"
    kotlin("plugin.serialization") version "2.4.21"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation:3.6.1")
    implementation("io.ktor:ktor-server-status-pages:3.6.1")
    implementation("io.ktor:ktor-server-swagger:3.6.1")
    implementation("io.ktor:ktor-server-routing-openapi:3.6.1")

    implementation("io.ktor:ktor-server-core-jvm:3.6.1")
    implementation("io.ktor:ktor-server-netty-jvm:3.6.1")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:3.6.1")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:3.6.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(exposedLibs.core)
    implementation(exposedLibs.jdbc)
    implementation(exposedLibs.dao)
    implementation("org.xerial:sqlite-jdbc:3.54.1.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("gamebacklog.MainKt")
}

kotlin {
    jvmToolchain(21)
}
