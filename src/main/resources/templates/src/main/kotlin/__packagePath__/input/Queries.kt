package @packageName@.input

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsQuery
import graphql.relay.Connection
import org.dema.graphql.dgs.pagination.RelayPageable
import @group@.DgsConstants
import @group@.types.User
import @group@.types.UsersFilter
import @group@.types.UsersQueries
import @group@.types.UsersSort
import @group@.user.profiles.service.UsersService

private val DUMMY_USERS_QUERIES: UsersQueries = UsersQueries()

@DgsComponent
class Queries(
    private val usersService: UsersService,
) {
    @DgsQuery
    fun users(): UsersQueries = DUMMY_USERS_QUERIES

    @DgsData(parentType = DgsConstants.USERS_QUERIES.TYPE_NAME)
    fun all(
        first: Int = 20,
        after: String?,
        sort: UsersSort = UsersSort.CREATED_AT_DESC,
        filter: UsersFilter?,
    ): Connection<User> {
        val relayPageable = RelayPageable(first = first, after = after, sortingKey = sort)
        return usersService.getAll(relayPageable, filter)
    }
}