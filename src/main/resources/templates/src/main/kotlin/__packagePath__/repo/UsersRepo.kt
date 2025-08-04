package @packageName@.repo

import org.dema.jooq.AbstractRepository
import org.springframework.stereotype.Component
import @group@.Tables.USERS
import @group@.tables.Users
import @group@.tables.records.UsersRecord

@Component
class UsersRepo : AbstractRepository<Users, UsersRecord>(
    table = USERS,
    baseCondition = USERS.DELETED_AT.isNull.and(USERS.ARCHIVED_AT.isNull),
)