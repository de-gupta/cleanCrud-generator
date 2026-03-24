<#-- Template for generating DomainModelBuilderFactory class -->
package ${basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}DomainModelBuilderFactory implements
ModelBuilderFactory${"<"}${modelBaseName()}DomainModel, ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder${">"}
{
@Override
public ${modelBaseName()}DomainModel.${modelBaseName()}DomainModelBuilder builder()
{
return ${modelBaseName()}DomainModelImpl.builder();
}
}