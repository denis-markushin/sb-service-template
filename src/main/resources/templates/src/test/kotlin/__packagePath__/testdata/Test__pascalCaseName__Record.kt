package @packageName@.testdata

import @group@.tables.records.@pascalCaseNamePlural@Record
import java.time.LocalDateTime
import java.util.UUID

object Test@pascalCaseName@Record {
    operator fun invoke(
        id: UUID = UUID.randomUUID(),
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
        archivedAt: LocalDateTime? = null,
        deletedAt: LocalDateTime? = null,
    ): @pascalCaseNamePlural@Record =
        @pascalCaseNamePlural@Record().apply {
            this.id = id
            this.createdAt = createdAt
            this.updatedAt = updatedAt
            this.archivedAt = archivedAt
            this.deletedAt = deletedAt
        }
}
