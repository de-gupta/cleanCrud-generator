<#-- Template for generating PersistenceModelBuilderFactory class -->
package ${basePackage}.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${modelName}PersistenceModelBuilderFactory
implements ModelBuilderFactory${"<"}${modelName}PersistenceModel, ${modelName}PersistenceModel.${modelName}PersistenceModelBuilder${">"}
{
@Override
public ${modelName}PersistenceModel.${modelName}PersistenceModelBuilder builder()
{
return ${modelName}PersistenceModelImpl.builder();
}
}