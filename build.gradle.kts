import com.orctom.gradle.archetype.ArchetypeGenerateTask

plugins {
    id("com.orctom.archetype") version "2.0.0"
    id("com.diffplug.spotless") version "6.25.0"
}

tasks.withType<ArchetypeGenerateTask>().configureEach {
    dependsOn("installGitHooks")

    extra["bindingProcessor"] = closureOf<MutableMap<String, Any?>> {
        val raw = this["name"] as String                // "my-service"
        val camel = raw.split('-', '_', ' ')
            .joinToString("") { it.replaceFirstChar(Char::uppercase) }  // "MyService"
        this["camelCaseName"] = camel
    }
}

spotless {
    lineEndings = com.diffplug.spotless.LineEnding.UNIX
    kotlin {
        target("**/*.kt")
    }
}

// Install pre-push hook
tasks.register<Copy>("installGitHooks") {
    description = "Install git hooks"
    group = "build"

    from("$rootDir/src/main/resources/templates/scripts")
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