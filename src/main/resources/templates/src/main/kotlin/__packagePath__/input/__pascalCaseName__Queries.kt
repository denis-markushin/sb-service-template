package @packageName@.input

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsQuery
import graphql.relay.Connection
import org.dema.graphql.dgs.pagination.RelayPageable
import @group@.DgsConstants
import @group@.types.@pascalCaseName@
import @group@.types.@pascalCaseName@Filter
import @group@.types.@pascalCaseName@Queries
import @group@.types.@pascalCaseName@Sort
import @packageName@.service.@pascalCaseName@Service
import java.util.UUID

private val DUMMY_@screamingSnakeCaseName@_QUERIES: @pascalCaseName@Queries = @pascalCaseName@Queries()

@DgsComponent
class @pascalCaseName@Queries(
    private val @camelCaseName@Service: @pascalCaseName@Service,
) {

    @DgsQuery
    fun @camelCaseNamePlural@(): @pascalCaseName@Queries = DUMMY_@screamingSnakeCaseName@_QUERIES

    @DgsData(parentType = DgsConstants.@screamingSnakeCaseName@_QUERIES.TYPE_NAME)
    fun byId(id: UUID): @pascalCaseName@ = @camelCaseName@Service.getById(id)

    @DgsData(parentType = DgsConstants.@screamingSnakeCaseName@_QUERIES.TYPE_NAME)
    fun all(
        first: Int = 20,
        after: String?,
        sort: @pascalCaseName@Sort = @pascalCaseName@Sort.CREATED_AT_DESC,
        filter: @pascalCaseName@Filter?,
    ): Connection<@pascalCaseName@> {
        val relayPageable = RelayPageable(first = first, after = after, sortingKey = sort)
        return @camelCaseName@Service.getAll(relayPageable, filter)
    }
}
