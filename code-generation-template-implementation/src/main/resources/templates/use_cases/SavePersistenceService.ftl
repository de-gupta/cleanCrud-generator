<#-- Template for generating SavePersistenceService class -->
package ${basePackage}.useCases.crud.save.infrastructure.persistence.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.transaction.PersistenceTransactionRunner;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SavePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.AbstractSavePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.SavePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
final class ${modelName}SavePersistenceService extends
		AbstractSavePersistenceService<Long, ${modelName}DomainModel, UUID, ${modelName}PersistenceModel>
		implements SavePersistenceService<Long, ${modelName}DomainModel>
{
	${modelName}SavePersistenceService(
			final SavePersistenceModelRepository<${modelName}PersistenceModel> repository,
			final DomainPersistenceModelAdapter<${modelName}DomainModel, ${modelName}PersistenceModel> modelAdapter,
			@Qualifier("${modelName?uncap_first}DomainPersistenceIDManagement") final DomainPersistenceIDManagement<Long, UUID> idManagement,
			final PersistenceTransactionRunner transactionRunner)
	{
		super(repository, modelAdapter, idManagement, transactionRunner);
	}
}