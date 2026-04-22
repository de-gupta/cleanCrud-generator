<#-- Template for generating FetchPersistenceService class -->
package ${aggregate().basePackage()}.useCases.crud.fetch.infrastructure.persistence.service;

import ${aggregate().basePackage()}.domain.model.${aggregate().baseName()}DomainModel;
import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.AbstractFetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${aggregate().baseName()}FetchPersistenceService
		extends
		AbstractFetchPersistenceService${"<"}Long, ${aggregate().baseName()}DomainModel,
		UUID, ${aggregate().baseName()}PersistenceModel${">"}
		implements
		FetchPersistenceService${"<"}Long, ${aggregate().baseName()}DomainModel${">"}
{
	${aggregate().baseName()}FetchPersistenceService(
			final FetchPersistenceModelRepository${"<"}${aggregate().baseName()}PersistenceModel, UUID${">"} repository,
			final DomainPersistenceModelAdapter${"<"}${aggregate().baseName()}DomainModel,
			${aggregate().baseName()}PersistenceModel${">"} modelAdapter,
			@Qualifier("${aggregate().beanNamePrefix()}DomainPersistenceIDAdapter") final
			DomainPersistenceIDAdapter${"<"}Long, UUID${">"} idAdapter)
	{
		super(repository, modelAdapter, idAdapter);
	}
}