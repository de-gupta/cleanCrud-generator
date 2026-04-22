<#-- Template for generating DomainPersistenceAdapterModelBuilderFactory class -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.domain.model.builder.ModelBuilderFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.DomainPersistenceAdapterModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Qualifier("${aggregate().beanNamePrefix()}DomainPersistenceAdapterModelBuilderFactory")
final class ${aggregate().baseName()}DomainPersistenceAdapterModelBuilderFactory
implements
ModelBuilderFactory${"<"}DomainPersistenceAdapterModel${"<"}Long, UUID${">"}, DomainPersistenceAdapterModel.Builder${"<"}Long,
UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel${">"}${">"}
{
@Override
public DomainPersistenceAdapterModel.Builder${"<"}Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel${">"} builder()
{
return ${aggregate().baseName()}DomainPersistenceAdapterModel.builder();
}
}