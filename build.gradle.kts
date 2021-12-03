import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id ("org.jetbrains.kotlin.plugin.noarg") version "1.4.32"
    id ("org.jetbrains.kotlin.plugin.allopen") version "1.4.32"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}
extra["snippetsDir"] = file("build/generated-snippets")
extra["springCloudGcpVersion"] = "2.0.4"
extra["springCloudVersion"] = "2020.0.4"
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("io.mockk:mockk:1.12.1")
    implementation("org.jetbrains.kotlin:kotlin-noarg:1.4.32")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.session:spring-session-core")
    implementation("io.jsonwebtoken:jjwt-api:0.11.1")
    implementation("org.aspectj:aspectjrt:1.8.9")
    implementation("io.arrow-kt:arrow-core:0.10.5")
    implementation("io.arrow-kt:arrow-fx:0.10.5")
    implementation("io.arrow-kt:arrow-optics:0.10.5")
    implementation("io.arrow-kt:arrow-syntax:0.10.5")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.1")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
