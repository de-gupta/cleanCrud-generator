<#-- Template for generating DeletePersistenceService class -->
package ${basePackage}.useCases.crud.delete.infrastructure.persistence.service;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
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
@Qualifier("${modelName?uncap_first}DeletePersistenceService")
final class ${modelName}DeletePersistenceService extends AbstractDeletePersistenceService<Long, UUID, ${modelName}PersistenceModel>
		implements DeletePersistenceService<Long>
{
	${modelName}DeletePersistenceService(
			final FetchPersistenceModelRepository<${modelName}PersistenceModel, UUID> fetchRepository,
			@Qualifier("${modelName?uncap_first}DeletePersistenceModelRepository") final DeletePersistenceModelRepository<UUID> deleteRepository,
			@Qualifier("${modelName?uncap_first}DomainPersistenceIDAdapter") final DomainPersistenceIDAdapter<Long, UUID> idAdapter,
			@Qualifier("${modelName?uncap_first}DomainPersistenceIDManagement") final DomainPersistenceIDManagement<Long, UUID> idManagement,
			final PersistenceTransactionRunner transactionRunner)
	{
		super(fetchRepository, deleteRepository, idAdapter, idManagement, transactionRunner);
	}
}