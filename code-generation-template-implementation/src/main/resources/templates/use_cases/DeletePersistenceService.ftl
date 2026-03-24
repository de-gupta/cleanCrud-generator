<#-- Template for generating DeletePersistenceService class -->
package ${basePackage()}.useCases.crud.delete.infrastructure.persistence.service;

import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.transaction.PersistenceTransactionRunner;
import de.gupta.clean.crud.template.useCases.crud.delete.application.service.DeletePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.service.AbstractDeletePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.service.DeletePersistenceModelRepository;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Qualifier("${beanNamePrefix()}DeletePersistenceService")
final class ${modelBaseName()}DeletePersistenceService extends AbstractDeletePersistenceService<Long, UUID, ${modelBaseName()}PersistenceModel>
		implements DeletePersistenceService<Long>
{
	${modelBaseName()}DeletePersistenceService(
			final FetchPersistenceModelRepository<${modelBaseName()}PersistenceModel, UUID> fetchRepository,
			@Qualifier("${beanNamePrefix()}DeletePersistenceModelRepository") final DeletePersistenceModelRepository<UUID> deleteRepository,
			@Qualifier("${beanNamePrefix()}DomainPersistenceIDAdapter") final DomainPersistenceIDAdapter<Long, UUID> idAdapter,
			@Qualifier("${beanNamePrefix()}DomainPersistenceIDManagement") final DomainPersistenceIDManagement<Long, UUID> idManagement,
			final PersistenceTransactionRunner transactionRunner)
	{
		super(fetchRepository, deleteRepository, idAdapter, idManagement, transactionRunner);
	}
}