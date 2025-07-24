package @packageName@.mapping.converter

import org.mapstruct.Mapper
import org.springframework.core.convert.converter.Converter
import @packageName@.config.MapstructConfig

// TODO: remove it. It`s sample code
@Mapper(config = MapstructConfig::class)
abstract class String2IntConverter : Converter<String, Int> {
    override fun convert(source: String): Int {
        return source.toInt()
    }
}