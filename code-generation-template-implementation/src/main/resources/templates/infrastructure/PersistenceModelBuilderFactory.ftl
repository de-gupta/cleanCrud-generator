<#-- Template for generating PersistenceModelBuilderFactory class -->
package ${aggregate().basePackage()}.infrastructure.persistence.repository;

import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import org.springframework.stereotype.Component;

@Component
final class ${aggregate().baseName()}PersistenceModelBuilderFactory
implements ModelBuilderFactory${"<"}${aggregate().baseName()}PersistenceModel, ${aggregate().baseName()}PersistenceModel.${aggregate().baseName()}PersistenceModelBuilder${">"}
{
@Override
public ${aggregate().baseName()}PersistenceModel.${aggregate().baseName()}PersistenceModelBuilder builder()
{
return ${aggregate().baseName()}PersistenceModelImpl.builder();
}
}