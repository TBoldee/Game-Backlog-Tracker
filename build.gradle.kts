plugins {
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.serialization") version "2.2.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-content-negotiation:3.6.1")
    implementation("io.ktor:ktor-server-status-pages:3.6.1")
    val ktorVersion = "3.3.3"

    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:3.6.1")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")

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
