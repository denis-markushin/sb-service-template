import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask

group = property("project.group") as String
version = property("project.version") as String

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.jooq.codegen)
    alias(libs.plugins.netflix.dgs.codegen)
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

    // spring cloud
    implementation("org.springframework.cloud:spring-cloud-starter-config")

    // db
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")

    // third party libs
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation(libs.spring.doc)
    implementation(libs.dema.jooq.utils)
    implementation(libs.findbugs)

    // mapstruct
    implementation(libs.bundles.mapstruct)
    kapt(libs.mapstruct.processor)
    kapt(libs.mapstruct.spring.extensions)

    // graphql
    implementation(libs.dema.graphql.starter)
    testImplementation("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter-test")

    // jooq
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.jooq:jooq:${libs.versions.jooq.get()}")
    jooqCodegen(libs.dema.jooq.liquibaseTestcontainers)

    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    testImplementation(libs.bundles.test)

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
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint(libs.versions.ktlint.get())
    }
}

val dashedGroup = group.toString().replace("-", "")

jooq {
    configuration {
        logging = org.jooq.meta.jaxb.Logging.DEBUG

        jdbc {
            driver = "org.testcontainers.jdbc.ContainerDatabaseDriver"
            url = "jdbc:tc:postgresql:17.5-alpine:///test-db"
        }

        generator {
            database {
                name = "org.dema.jooq.liquibase.LiquibasePostgresTcDatabase"
                includes = ".*"
                excludes = "databasechangelog|databasechangeloglock"
                inputSchema = "public"
                properties {
                    property {
                        key = "liquibaseChangelogFile"
                        value = "$projectDir/src/main/resources/liquibase/changelog-master.yml"
                    }
                }
            }
            generate {
                isPojos = false
                isDaos = false
                isRecordsImplementingRecordN = false
                isNullableAnnotation = true
                isNonnullAnnotation = true
                isValidationAnnotations = true
            }
            target {
                directory = "${layout.buildDirectory.get()}/generated/sources/jooq"
                packageName = dashedGroup
            }
        }
    }
}

tasks.generateJava {
    packageName = dashedGroup
    generateDocs = true
    generateClient = true
    generateBoxedTypes = true
    snakeCaseConstantNames = true
    addGeneratedAnnotation = true
    generateKotlinNullableClasses = true
    generateKotlinClosureProjections = true
    typeMapping =
        mutableMapOf(
            "UUID" to "java.util.UUID",
            "Base64" to "kotlin.String",
            "Generated" to "javax.annotation.Generated",
            "LocalDateTime" to "java.time.LocalDateTime",
        )
}

tasks.compileKotlin {
    dependsOn("jooqCodegen")
}

tasks.withType<KaptGenerateStubsTask> {
    dependsOn("jooqCodegen")
}

tasks.processResources {
    val substitutionTokens =
        rootProject.properties
            .map { "project.${it.key}" to (it.value?.toString() ?: "unknown") }
            .toMap()
    filter<ReplaceTokens>("tokens" to substitutionTokens)
}

tasks.assemble {
    dependsOn("spotlessInstallGitPrePushHook")
}

tasks.named("spotlessInstallGitPrePushHook") {
    onlyIf { !file(".git/hooks/pre-push").exists() }
}