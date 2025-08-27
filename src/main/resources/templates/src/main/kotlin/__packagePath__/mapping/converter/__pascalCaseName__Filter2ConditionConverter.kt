package @packageName@.mapping.converter

import org.jooq.Condition
import org.jooq.impl.DSL.noCondition
import org.jooq.impl.DSL.or
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import @group@.Tables.@screamingSnakeCaseNamePlural@
import @group@.types.@pascalCaseName@Filter

@Component
class @pascalCaseName@Filter2ConditionConverter : Converter<@pascalCaseName@Filter, Condition> {
    override fun convert(src: @pascalCaseName@Filter): Condition = listOfNotNull(
        queryCondition(src.query),
    ).fold(noCondition(), Condition::and)

    private fun queryCondition(query: String?): Condition = if (query.isNullOrBlank()) {
        noCondition()
    } else {
        val likeIgnoreCaseById = @screamingSnakeCaseNamePlural@.ID.likeIgnoreCase("%$query%")
        or(likeIgnoreCaseById)
    }
}
