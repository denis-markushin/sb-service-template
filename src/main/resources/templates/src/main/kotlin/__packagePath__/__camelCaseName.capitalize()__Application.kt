package @packageName@

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class @camelCaseName@Application

fun main(args: Array<String>) {
    runApplication<@camelCaseName@Application>(*args)
}