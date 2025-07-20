package @packageName@

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Import(TestcontainersConfiguration::class)
@SpringBootTest
@ActiveProfiles("integration-test")
class @camelCaseName.capitalize()@ApplicationTest {
    @Test
    fun contextLoads() {
    }
}