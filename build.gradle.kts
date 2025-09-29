plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.gitlab.arturbosch.detekt") version "1.23.4"
    kotlin("plugin.jpa") version "1.3.72"
    id("org.flywaydb.flyway") version "8.0.3"
    id("jacoco")
//    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
}

group = "br.com.fiap"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

val excludePaths: Iterable<String> = listOf(
    "br/com/fiap/ordermanagement/OrderManagementApplication**",
    "br/com/fiap/ordermanagement/application/config**",
    "br/com/fiap/ordermanagement/application/responses**",
    "br/com/fiap/ordermanagement/application/requests**",
    "br/com/fiap/ordermanagement/application/handler**",
    "br/com/fiap/ordermanagement/domain/enums**",
    "br/com/fiap/ordermanagement/domain/model**",
    "br/com/fiap/ordermanagement/domain/entities**"
)

dependencies {
    // SPRING
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // SWAGGER
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // DBA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.7.2")

    // FLYWAY
    implementation("org.flywaydb:flyway-core:9.3.0")

    // JSON NAMING
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // FEIGN
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    // TEST
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.mockk:mockk:1.13.10")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // JACOCO
    testImplementation("org.jacoco:org.jacoco.agent:0.8.7")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

detekt {
    config.setFrom(file("detekt.yml"))
    buildUponDefaultConfig = true
    allRules = true
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0")
    }
}

jacoco {
    toolVersion = "0.8.10"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // Garante que os testes rodem antes do report

    reports {
        xml.required.set(true) // útil para integração com CI (ex: Sonar)
        html.required.set(true) // abre no navegador: build/reports/jacoco/test/html/index.html
        csv.required.set(false) // opcional
    }

    // opcional: define os arquivos que devem ser analisados
    classDirectories.setFrom(
        fileTree(
            "build/classes/kotlin/main"
        ) {
            exclude(excludePaths)
        }
    )
}
