<#-- Template for generating SavePersistenceService class -->
package ${basePackage()}.useCases.crud.save.infrastructure.persistence.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
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
final class ${modelBaseName()}SavePersistenceService extends
		AbstractSavePersistenceService<Long, ${modelBaseName()}DomainModel, UUID, ${modelBaseName()}PersistenceModel>
		implements SavePersistenceService<Long, ${modelBaseName()}DomainModel>
{
	${modelBaseName()}SavePersistenceService(
			final SavePersistenceModelRepository<${modelBaseName()}PersistenceModel> repository,
			final DomainPersistenceModelAdapter<${modelBaseName()}DomainModel, ${modelBaseName()}PersistenceModel> modelAdapter,
			@Qualifier("${modelName()?uncap_first}DomainPersistenceIDManagement") final DomainPersistenceIDManagement<Long, UUID> idManagement,
			final PersistenceTransactionRunner transactionRunner)
	{
		super(repository, modelAdapter, idManagement, transactionRunner);
	}
}