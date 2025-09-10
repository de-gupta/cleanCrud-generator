<#-- Template for generating DomainModelBuilderFactory class -->
package ${basePackage}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}DomainModelBuilderFactory implements
ModelBuilderFactory${"<"}${modelName}DomainModel, ${modelName}DomainModel.${modelName}DomainModelBuilder${">"}
{
@Override
public ${modelName}DomainModel.${modelName}DomainModelBuilder builder()
{
return ${modelName}DomainModelImpl.builder();
}
}