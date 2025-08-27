package @packageName@.integration

import org.jooq.DSLContext
import org.jooq.UpdatableRecord
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.ActiveProfiles
import @group@.Tables.@screamingSnakeCaseNamePlural@
import java.util.UUID

private val TABLES_TO_CLEANUP = listOf(@screamingSnakeCaseNamePlural@)

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

    protected fun <R : UpdatableRecord<R>> R.storeRec(): R {
        this.attach(dsl.configuration())
        this.store()
        return this
    }
}
