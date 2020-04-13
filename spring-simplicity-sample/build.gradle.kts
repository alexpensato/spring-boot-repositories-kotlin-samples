import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springBootVersion = "2.2.6.RELEASE"
val springVersion = "5.2.5.RELEASE"
val jacksonVersion = "2.10.3"
val postgresVersion = "9.4.1208-atlassian-hosted"
val hikariVersion = "3.4.2"

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    war
    kotlin("jvm") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
}

group = "org.pensatocode.simplicity.sample"
version = "1.4.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    jcenter()

    maven { url = uri("https://maven.atlassian.com/3rdparty/") }
//    maven { url "https://repo.eclipse.org/content/repositories/paho-snapshots/" }
//    maven { url "https://raw.github.com/eburtsev/gdata-maven/master/" }

    flatDir {
        dirs("libs")
    }
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter-tomcat:${springBootVersion}")

    // Spring Simplicity
    implementation(":spring-simplicity-kotlin")
    implementation("org.springframework:spring-jdbc:${springVersion}")
    implementation("org.springframework.data:spring-data-commons:${springBootVersion}")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Database
    implementation("postgresql:postgresql:${postgresVersion}")
    implementation("com.zaxxer:HikariCP:${hikariVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
