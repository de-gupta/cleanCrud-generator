<#-- Template for generating UpdatePersistenceService class -->
package ${basePackage()}.useCases.crud.update.infrastructure.persistence.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.SavePersistenceModelRepository;
import de.gupta.clean.crud.template.useCases.crud.update.application.service.UpdatePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.service.AbstractUpdatePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.service.UpdatePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
final class ${modelBaseName()}UpdatePersistenceService
		extends AbstractUpdatePersistenceService<Long, ${modelBaseName()}DomainModel, UUID, ${modelBaseName()}PersistenceModel>
		implements UpdatePersistenceService<Long, ${modelBaseName()}DomainModel>
{
	${modelBaseName()}UpdatePersistenceService(
			final FetchPersistenceModelRepository<${modelBaseName()}PersistenceModel, UUID> fetchRepository,
			final SavePersistenceModelRepository<${modelBaseName()}PersistenceModel> saveRepository,
			final UpdatePersistenceModelRepository<${modelBaseName()}PersistenceModel> updateRepository,
			final DomainPersistenceModelAdapter<${modelBaseName()}DomainModel, ${modelBaseName()}PersistenceModel> modelAdapter,
			@Qualifier("${beanNamePrefix()}DomainPersistenceIDAdapter") final DomainPersistenceIDAdapter<Long, UUID> idAdapter,
			@Qualifier("${beanNamePrefix()}DomainPersistenceIDManagement") final DomainPersistenceIDManagement<Long, UUID> idManagement)
	{
		super(fetchRepository, saveRepository, updateRepository, modelAdapter, idAdapter, idManagement);
	}
}
