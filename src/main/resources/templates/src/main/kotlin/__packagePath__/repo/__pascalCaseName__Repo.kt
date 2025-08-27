package @packageName@.repo

import org.dema.jooq.AbstractRepository
import org.springframework.stereotype.Component
import ru.easyway.efko.Tables.@screamingSnakeCaseNamePlural@
import ru.easyway.efko.tables.@pascalCaseNamePlural@
import ru.easyway.efko.tables.records.@pascalCaseNamePlural@Record

@Component
class @pascalCaseName@Repo : AbstractRepository<@pascalCaseNamePlural@, @pascalCaseNamePlural@Record>(
    table = @screamingSnakeCaseNamePlural@,
    baseCondition = @screamingSnakeCaseNamePlural@.DELETED_AT.isNull.and(@screamingSnakeCaseNamePlural@.ARCHIVED_AT.isNull),
)
