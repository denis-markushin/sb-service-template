package @packageName@.integration

import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Table
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.ActiveProfiles

private val TABLES_TO_CLEANUP = listOf<List<Table<Record>>>()

@ActiveProfiles("integration-test")
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
abstract class AbstractIntegrationTest {
    @Autowired
    protected lateinit var dsl: DSLContext

    @BeforeEach
    fun cleanup() {
        TABLES_TO_CLEANUP.forEach { dsl.truncate(it).cascade().execute() }
    }

    protected fun Collection<UpdatableRecord<*>>.store(): Collection<UpdatableRecord<*>> {
        dsl.batchStore(this).execute()
        return this
    }

    protected fun Array<UpdatableRecord<*>>.store(): Array<UpdatableRecord<*>> {
        dsl.batchStore(*this).execute()
        return this
    }
}

fun String.withRandomSuffix(length: Int = 4): String =
    this + "_" +
        UUID
            .randomUUID()
            .toString()
            .replace("-", "")
            .take(length)