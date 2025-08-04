package @packageName@.mapping.converter

import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter
import @group@.tables.records.UsersRecord
import @group@.types.User
import @packageName@.config.MapstructConfig

// TODO: remove it. It`s sample code
@Mapper(config = MapstructConfig::class)
abstract class UsersRecord2UserConverter : Converter<UsersRecord, User> {
    override fun convert(source: UsersRecord): User {
        return User(
            id = { source.id.toString() },
            name = { source.fullName },
        )
    }
}