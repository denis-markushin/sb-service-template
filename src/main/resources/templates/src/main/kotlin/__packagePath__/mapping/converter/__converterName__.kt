package @packageName@.mapping.converter

import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter
import @group@.tables.records.@pascalCaseNamePlural@Record
import @group@.types.@pascalCaseName@
import @packageName@.config.MapstructConfig

@Mapper(config = MapstructConfig::class)
abstract class @pascalCaseNamePlural@Record2@pascalCaseName@Converter : Converter<@pascalCaseNamePlural@Record, @pascalCaseName@> {
    override fun convert(source: @pascalCaseNamePlural@Record): @pascalCaseName@ {
        return @pascalCaseName@(
            id = { source.id },
        )
    }
}
