package @packageName@.config

import org.mapstruct.MapperConfig
import org.mapstruct.ReportingPolicy
import org.mapstruct.extensions.spring.SpringMapperConfig

@MapperConfig(
    componentModel = "spring",
    uses = [ConversionServiceAdapter::class],
    unmappedTargetPolicy = ReportingPolicy.ERROR,
)
@SpringMapperConfig(conversionServiceBeanName = "mvcConversionService")
interface MapstructConfig