package @packageName@.service

import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import graphql.relay.Connection
import org.dema.graphql.dgs.pagination.RelayPageable
import org.dema.graphql.dgs.utils.OrderByClausesMapping
import org.dema.graphql.dgs.utils.convert
import org.dema.graphql.dgs.utils.getAllBy
import org.dema.graphql.dgs.utils.map
import org.dema.graphql.dgs.utils.toConnection
import org.jooq.Condition
import org.jooq.impl.DSL.noCondition
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service
import @group@.Tables.@screamingSnakeCaseNamePlural@
import @group@.types.@pascalCaseName@
import @group@.types.@pascalCaseName@Filter
import @group@.types.@pascalCaseName@Sort
import @packageName@.repo.@pascalCaseName@Repo
import java.util.UUID

@Service
class @pascalCaseName@Service(
    private val cs: ConversionService,
    private val @camelCaseName@Repo: @pascalCaseName@Repo,
) {
    init {
        OrderByClausesMapping.register {
            key(@pascalCaseName@Sort.CREATED_AT_DESC).fields(@screamingSnakeCaseNamePlural@.CREATED_AT.desc(), @screamingSnakeCaseNamePlural@.ID.asc())
        }
    }

    fun getById(id: UUID): @pascalCaseName@ = @camelCaseName@Repo
        .getOneBy { it.ID.eq(id) }
        ?.let { cs.convert<@pascalCaseName@>(it) }
        ?: throw DgsEntityNotFoundException()

    fun getAll(
        relayPageable: RelayPageable,
        filter: @pascalCaseName@Filter?,
    ): Connection<@pascalCaseName@> {
        val condition: Condition = cs.convert(filter) ?: noCondition()
        return @camelCaseName@Repo
            .getAllBy(relayPageable = relayPageable, where = condition)
            .toConnection(relayPageable)
            .map(cs::convert)
    }
}
