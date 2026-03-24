<#-- Template for generating PersistenceModelBuilderFactory class -->
package ${basePackage()}.infrastructure.persistence.repository;

import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${modelBaseName()}PersistenceModelBuilderFactory
implements ModelBuilderFactory${"<"}${modelBaseName()}PersistenceModel, ${modelBaseName()}PersistenceModel.${modelBaseName()}PersistenceModelBuilder${">"}
{
@Override
public ${modelBaseName()}PersistenceModel.${modelBaseName()}PersistenceModelBuilder builder()
{
return ${modelBaseName()}PersistenceModelImpl.builder();
}
}