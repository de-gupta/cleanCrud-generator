<#-- Template for generating DomainPersistenceAdapterModelBuilderFactory class -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.DomainPersistenceAdapterModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Qualifier("${modelName()?uncap_first}DomainPersistenceAdapterModelBuilderFactory")
final class ${modelBaseName()}DomainPersistenceAdapterModelBuilderFactory
implements
ModelBuilderFactory${"<"}DomainPersistenceAdapterModel${"<"}Long, UUID${">"}, DomainPersistenceAdapterModel.Builder${"<"}Long,
UUID, ${modelBaseName()}DomainPersistenceAdapterModel${">"}${">"}
{
@Override
public DomainPersistenceAdapterModel.Builder${"<"}Long, UUID, ${modelBaseName()}DomainPersistenceAdapterModel${">"} builder()
{
return ${modelBaseName()}DomainPersistenceAdapterModel.builder();
}
}