<#-- Template for generating DeletePersistenceService class -->
package ${aggregate().basePackage()}.useCases.crud.delete.infrastructure.persistence.service;

import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.DeletePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.service.AbstractDeletePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.service.DeletePersistenceModelRepository;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Qualifier("${aggregate().beanNamePrefix()}DeletePersistenceService")
final class ${aggregate().baseName()}DeletePersistenceService extends AbstractDeletePersistenceService<Long, UUID, ${aggregate().baseName()}PersistenceModel>
		implements DeletePersistenceService<Long>
{
	${aggregate().baseName()}DeletePersistenceService(
			final FetchPersistenceModelRepository<${aggregate().baseName()}PersistenceModel, UUID> fetchRepository,
			@Qualifier("${aggregate().beanNamePrefix()}DeletePersistenceModelRepository") final DeletePersistenceModelRepository<UUID> deleteRepository,
			@Qualifier("${aggregate().beanNamePrefix()}DomainPersistenceIDAdapter") final DomainPersistenceIDAdapter<Long, UUID> idAdapter,
			@Qualifier("${aggregate().beanNamePrefix()}DomainPersistenceIDManagement") final DomainPersistenceIDManagement<Long, UUID> idManagement)
	{
		super(fetchRepository, deleteRepository, idAdapter, idManagement);
	}
}
