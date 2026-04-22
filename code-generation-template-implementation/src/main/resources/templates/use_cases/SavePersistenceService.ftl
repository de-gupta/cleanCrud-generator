<#-- Template for generating SavePersistenceService class -->
package ${aggregate().basePackage()}.useCases.crud.save.infrastructure.persistence.service;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.useCases.crud.save.application.service.SavePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.AbstractSavePersistenceService;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.SavePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
final class ${aggregate().baseName()}SavePersistenceService extends
		AbstractSavePersistenceService<Long, ${aggregate().baseName()}DomainModel, UUID, ${aggregate().baseName()}PersistenceModel>
		implements SavePersistenceService<Long, ${aggregate().baseName()}DomainModel>
{
	${aggregate().baseName()}SavePersistenceService(
			final SavePersistenceModelRepository<${aggregate().baseName()}PersistenceModel> repository,
			final DomainPersistenceModelAdapter<${aggregate().baseName()}DomainModel, ${aggregate().baseName()}PersistenceModel> modelAdapter,
			@Qualifier("${aggregate().beanNamePrefix()}DomainPersistenceIDManagement") final DomainPersistenceIDManagement<Long, UUID> idManagement)
	{
		super(repository, modelAdapter, idManagement);
	}
}
