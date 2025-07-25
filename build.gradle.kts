import com.orctom.gradle.archetype.ArchetypeGenerateTask

plugins {
    id("com.orctom.archetype") version "2.0.0"
    id("com.diffplug.spotless") version "7.2.1"
}

tasks.withType<ArchetypeGenerateTask>().configureEach {
    dependsOn("spotlessInstallGitPrePushHook")

    extra["bindingProcessor"] = closureOf<MutableMap<String, Any?>> {
        val raw = this["name"] as String                // "my-service"
        val camel = raw.split('-', '_', ' ')
            .joinToString("") { it.replaceFirstChar(Char::uppercase) }  // "MyService"
        this["camelCaseName"] = camel
    }
}

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