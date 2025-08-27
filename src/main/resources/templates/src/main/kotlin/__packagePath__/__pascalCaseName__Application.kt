package @packageName@

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class @pascalCaseName@Application

fun main(args: Array<String>) {
    runApplication<@pascalCaseName@Application>(*args)
}
