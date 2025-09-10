<#-- Template for generating DomainResponseBuilder class -->
package ${basePackage}.domain.mapping.fetch;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.domain.model.dto.${modelName}DomainModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}DomainResponseBuilder implements
DomainResponseBuilder${"<"}${modelName}DomainModel, ${modelName}DomainModelResponse${">"}
{
@Override
public ${modelName}DomainModelResponse toResponse(final ${modelName}DomainModel ${modelName?uncap_first}DomainModel)
{
return ${modelName}DomainModelResponse.fromDomainModel(${modelName?uncap_first}DomainModel);
}
}