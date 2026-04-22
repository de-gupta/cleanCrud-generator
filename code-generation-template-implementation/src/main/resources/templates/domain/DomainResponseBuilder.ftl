<#-- Template for generating DomainResponseBuilder class -->
package ${aggregate().basePackage()}.domain.mapping.fetch;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.domain.model.dto.${aggregate().baseName()}DomainModelResponse;
import de.gupta.clean.crud.template.domain.mapping.fetch.DomainResponseBuilder;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}DomainResponseBuilder implements
DomainResponseBuilder${"<"}${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModelResponse${">"}
{
@Override
public ${aggregate().baseName()}DomainModelResponse toResponse(final ${aggregate().baseName()}DomainModel ${aggregate().beanNamePrefix()}DomainModel)
{
return ${aggregate().baseName()}DomainModelResponse.fromDomainModel(${aggregate().beanNamePrefix()}DomainModel);
}
}