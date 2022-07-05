import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.spring") version "1.7.0"
}

group = "dev.d1s"
version = "0.1.0-beta.0"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

val teabagsVersion: String by project
val kmLogVersion: String by project
val starterAdviceVersion: String by project
val starterSimpleSecurityVersion: String by project
val liquibaseVersion: String by project
val longPollingStarterVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("com.github.d1s-utils.teabags:teabag-spring-web:$teabagsVersion")
    implementation("com.github.d1s-utils.teabags:teabag-dto:$teabagsVersion")
    implementation("com.github.d1s-utils.teabags:teabag-stdlib:$teabagsVersion")
    implementation("org.lighthousegames:logging-jvm:$kmLogVersion")
    implementation("com.github.d1s-utils:spring-boot-starter-advice:$starterAdviceVersion")
    implementation("com.github.d1s-utils:spring-boot-starter-simple-security:$starterSimpleSecurityVersion")
    implementation("com.github.d1s-utils.long-polling:spring-boot-starter-lp-server-web:$longPollingStarterVersion")
    implementation("org.mariadb.jdbc:mariadb-java-client")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

configurations["implementation"].exclude(
    "org.springframework.boot",
    "spring-boot-starter-tomcat"
)

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events.addAll(
            setOf(
                org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
                org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
            )
        )
    }
}

sourceSets.getByName("test") {
    java.srcDir("./test")
}