import org.apache.tools.ant.filters.ReplaceTokens

group = property("project.group") as String
version = property("project.version") as String

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spotless)
    alias(libs.plugins.kapt)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(platform(libs.dema.platform))
    developmentOnly(platform(libs.dema.platform))

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // spring boot
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jooq")

    // spring cloud
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    // third party libs
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation(libs.spring.doc)
    implementation(libs.dema.jooq.utils)
    implementation(libs.mapstruct.core)
    implementation(libs.mapstruct.spring)

    kapt(libs.mapstruct.processor)
    kapt(libs.mapstruct.spring.extensions)

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    lineEndings = com.diffplug.spotless.LineEnding.UNIX
    kotlin {
        target("**/*.kt")
        targetExclude("${layout.buildDirectory}/**/*.kt")
        ktlint(libs.versions.ktlint.get()).setEditorConfigPath(rootProject.file(".editorconfig").path)
        toggleOffOn()
        trimTrailingWhitespace()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint(libs.versions.ktlint.get())
    }
}

tasks.processResources {
    val substitutionTokens =
        rootProject.properties
            .map { "project.${it.key}" to (it.value?.toString() ?: "unknown") }
            .toMap()
    filter<ReplaceTokens>("tokens" to substitutionTokens)
}

// Install pre-push hook
tasks.register<Copy>("installGitHooks") {
    description = "Install git hooks"
    group = "build"

    from("$rootDir/scripts")
    into("$rootDir/.git/hooks")

    filePermissions {
        user {
            read = true
            write = true
            execute = true
        }
        group {
            read = true
            write = true
            execute = true
        }
        other {
            read = true
            execute = false
        }
    }
}

tasks.assemble {
    dependsOn("installGitHooks")
}