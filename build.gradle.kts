import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    id("io.spring.dependency-management") version "1.0.8.RELEASE" apply false
    kotlin("plugin.spring") version "1.3.50" apply false
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.50" apply false
    id("org.springframework.boot") version "2.1.8.RELEASE" apply false
}

group = "skb-proj"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Below are all Kotlin JVM projects and here we set them up
rootProject
    .allprojects
    .filter {
        it in setOf(
            project(":cache")
        )
    }
    .onEach {

        group = rootProject.group
        version = rootProject.version

        dependencies {
            implementation(kotlin("stdlib-jdk8"))
        }

    }
    .forEach { _ -> }


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
}
