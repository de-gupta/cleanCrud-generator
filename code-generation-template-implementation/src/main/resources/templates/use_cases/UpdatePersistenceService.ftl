<#-- Template for generating UpdatePersistenceService class -->
package ${aggregate().basePackage()}.useCases.crud.update.infrastructure.persistence.service;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
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
final class ${aggregate().baseName()}UpdatePersistenceService
		extends AbstractUpdatePersistenceService<Long, ${aggregate().baseName()}DomainModel, UUID, ${aggregate().baseName()}PersistenceModel>
		implements UpdatePersistenceService<Long, ${aggregate().baseName()}DomainModel>
{
	${aggregate().baseName()}UpdatePersistenceService(
			final FetchPersistenceModelRepository<${aggregate().baseName()}PersistenceModel, UUID> fetchRepository,
			final SavePersistenceModelRepository<${aggregate().baseName()}PersistenceModel> saveRepository,
			final UpdatePersistenceModelRepository<${aggregate().baseName()}PersistenceModel> updateRepository,
			final DomainPersistenceModelAdapter<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}PersistenceModel> modelAdapter,
			@Qualifier("${aggregate().beanNamePrefix()}DomainPersistenceIDAdapter") final DomainPersistenceIDAdapter<Long, UUID> idAdapter,
			@Qualifier("${aggregate().beanNamePrefix()}DomainPersistenceIDManagement") final DomainPersistenceIDManagement<Long, UUID> idManagement)
	{
		super(fetchRepository, saveRepository, updateRepository, modelAdapter, idAdapter, idManagement);
	}
}
