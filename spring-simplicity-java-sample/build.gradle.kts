val springBootVersion = "2.2.6.RELEASE"
val springVersion = "5.2.5.RELEASE"
val h2Version = "1.4.200"
val postgresVersion = "9.4.1208-atlassian-hosted"
val hikariVersion = "3.4.2"

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("java")
    war
}

group = "org.pensatocode.simplicity.sample"
version = "1.4.0"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    jcenter()

    maven { url = uri("https://maven.atlassian.com/3rdparty/") }

    flatDir {
        dirs("libs")
    }
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat:${springBootVersion}")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Spring Simplicity
    implementation(":spring-simplicity-java")
    implementation("org.springframework:spring-jdbc:${springVersion}")
    implementation("org.springframework.data:spring-data-commons:${springBootVersion}")

    // Database
    runtimeOnly("postgresql:postgresql:${postgresVersion}")
    implementation("com.zaxxer:HikariCP:${hikariVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${springBootVersion}") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

