import com.orctom.gradle.archetype.ArchetypeGenerateTask

plugins {
    id("com.orctom.archetype") version "2.0.0"
}

tasks.withType<ArchetypeGenerateTask>().configureEach {
    extra["bindingProcessor"] = closureOf<MutableMap<String, Any?>> {
        val raw = this["name"] as String                // "my-service"
        val camel = raw.split('-', '_', ' ')
            .joinToString("") { it.replaceFirstChar(Char::uppercase) }  // "MyService"
        this["camelCaseName"] = camel
    }
}