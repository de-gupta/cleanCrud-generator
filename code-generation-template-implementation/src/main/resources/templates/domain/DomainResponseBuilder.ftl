<#-- Template for generating DomainResponseBuilder class -->
package ${basePackage()}.domain.mapping.fetch;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.domain.model.dto.${modelBaseName()}DomainModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}DomainResponseBuilder implements
DomainResponseBuilder${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModelResponse${">"}
{
@Override
public ${modelBaseName()}DomainModelResponse toResponse(final ${modelBaseName()}DomainModel ${beanNamePrefix()}DomainModel)
{
return ${modelBaseName()}DomainModelResponse.fromDomainModel(${beanNamePrefix()}DomainModel);
}
}