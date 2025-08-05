package @packageName@

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import @packageName@.integration.AbstractIntegrationTest

class @camelCaseName.capitalize()@ApplicationTest : AbstractIntegrationTest() {
    @Test
    fun contextLoads() {
    }
}