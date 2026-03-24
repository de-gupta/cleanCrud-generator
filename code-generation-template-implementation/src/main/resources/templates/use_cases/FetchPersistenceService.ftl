<#-- Template for generating FetchPersistenceService class -->
package ${basePackage()}.useCases.crud.fetch.infrastructure.persistence.service;

import ${basePackage()}.domain.model.${modelBaseName()}DomainModel;
import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.AbstractFetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${modelBaseName()}FetchPersistenceService
		extends
		AbstractFetchPersistenceService${"<"}Long, ${modelBaseName()}DomainModel,
		UUID, ${modelBaseName()}PersistenceModel${">"}
		implements
		FetchPersistenceService${"<"}Long, ${modelBaseName()}DomainModel${">"}
{
	${modelBaseName()}FetchPersistenceService(
			final FetchPersistenceModelRepository${"<"}${modelBaseName()}PersistenceModel, UUID${">"} repository,
			final DomainPersistenceModelAdapter${"<"}${modelBaseName()}DomainModel,
			${modelBaseName()}PersistenceModel${">"} modelAdapter,
			@Qualifier("${beanNamePrefix()}DomainPersistenceIDAdapter") final
			DomainPersistenceIDAdapter${"<"}Long, UUID${">"} idAdapter)
	{
		super(repository, modelAdapter, idAdapter);
	}
}