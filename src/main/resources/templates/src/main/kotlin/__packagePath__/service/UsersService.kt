package ru.easyway.efko.user.profiles.service

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
import @group@.Tables.USERS
import @group@.types.User
import @group@.types.UsersFilter
import @group@.types.UsersSort
import @packageName@.repo.UsersRepo

@Service
class UsersService(
    private val cs: ConversionService,
    private val usersRepo: UsersRepo,
) {
    init {
        OrderByClausesMapping.register {
            key(UsersSort.CREATED_AT_DESC).fields(USERS.CREATED_AT.desc())
        }
    }

    fun getAll(
        relayPageable: RelayPageable,
        filter: UsersFilter?,
    ): Connection<User> {
        val condition: Condition = cs.convert(filter) ?: noCondition()
        return usersRepo
            .getAllBy(relayPageable = relayPageable, where = condition)
            .toConnection(relayPageable)
            .map(cs::convert)
    }
}