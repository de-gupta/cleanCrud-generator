<#-- Template for generating UpdatePersistenceService class -->
package ${basePackage}.useCases.crud.update.infrastructure.persistence.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.transaction.PersistenceTransactionRunner;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.SavePersistenceModelRepository;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdatePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.service.AbstractUpdatePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.service.UpdatePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
final class ${modelName}UpdatePersistenceService
		extends AbstractUpdatePersistenceService<Long, ${modelName}DomainModel, UUID, ${modelName}PersistenceModel>
		implements UpdatePersistenceService<Long, ${modelName}DomainModel>
{
	${modelName}UpdatePersistenceService(
			final FetchPersistenceModelRepository<${modelName}PersistenceModel, UUID> fetchRepository,
			final SavePersistenceModelRepository<${modelName}PersistenceModel> saveRepository,
			final UpdatePersistenceModelRepository<${modelName}PersistenceModel> updateRepository,
			final DomainPersistenceModelAdapter<${modelName}DomainModel, ${modelName}PersistenceModel> modelAdapter,
			@Qualifier("${modelName?uncap_first}DomainPersistenceIDAdapter") final DomainPersistenceIDAdapter<Long, UUID> idAdapter,
			@Qualifier("${modelName?uncap_first}DomainPersistenceIDManagement") final DomainPersistenceIDManagement<Long, UUID> idManagement,
			final PersistenceTransactionRunner transactionRunner)
	{
		super(fetchRepository, saveRepository, updateRepository, modelAdapter, idAdapter, idManagement,
				transactionRunner);
	}
}