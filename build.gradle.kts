import com.orctom.gradle.archetype.ArchetypeGenerateTask
import org.gradle.internal.extensions.stdlib.capitalized
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("com.orctom.archetype") version "2.0.0"
    id("com.diffplug.spotless") version "7.2.1"
}

tasks.withType<ArchetypeGenerateTask>().configureEach {
    dependsOn("spotlessInstallGitPrePushHook")

    extra["bindingProcessor"] = closureOf<MutableMap<String, Any?>> {
        val name = this["name"] as String // "my-service"
        this["pascalCaseName"] = name.toPascalCase() // "MyService"
        this["pascalCaseNamePlural"] = name.toPascalCase() + "s" // "MyServices"
        this["camelCaseName"] = name.toCamelCase() // "myService"
        this["camelCaseNamePlural"] = name.toCamelCase() + "s" // "myServices"
        this["snakeCaseName"] = name.toSnakeCase() // "my_service"
        this["screamingSnakeCaseName"] = name.toScreamingSnakeCase() // "MY_SERVICE"
        this["screamingSnakeCaseNamePlural"] = name.toScreamingSnakeCase() + "S" // "MY_SERVICES"
        this["converterName"] = "${name.toPascalCase()}sRecord2${name.toPascalCase()}Converter"
    }

    System.setProperty(
        "com.orctom.gradle.archetype.binding.date_yyyyMMddHHmm",
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")),
    )
}

fun String.splitByWords() = split('-', '_', ' ').filter { it.isNotEmpty() }

fun String.toCamelCase(): String = splitByWords()
    .joinToString("") { it.replaceFirstChar(Char::uppercase) }
    .replaceFirstChar { it.lowercase() }

fun String.toPascalCase() = this.toCamelCase().capitalized()
fun String.toSnakeCase(): String = splitByWords().joinToString("_").uppercase()
fun String.toScreamingSnakeCase(): String = toSnakeCase().uppercase()

spotless {
    lineEndings = com.diffplug.spotless.LineEnding.UNIX

    format("misc") {
        target("**/gradlew", "**/gradlew.bat", ".gitignore")
    }

    kotlin {
        target("**/*.kt")
    }
}

tasks.named("spotlessInstallGitPrePushHook") {
    onlyIf { !file(".git/hooks/pre-push").exists() }
}
