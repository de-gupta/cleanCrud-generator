<#-- Template for generating DomainModelBuilderFactory class -->
package ${aggregate().basePackage()}.domain.model;

import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}DomainModelBuilderFactory implements
ModelBuilderFactory${"<"}${aggregate().baseName()}DomainModel, ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder${">"}
{
@Override
public ${aggregate().baseName()}DomainModel.${aggregate().baseName()}DomainModelBuilder builder()
{
return ${aggregate().baseName()}DomainModelImpl.builder();
}
}