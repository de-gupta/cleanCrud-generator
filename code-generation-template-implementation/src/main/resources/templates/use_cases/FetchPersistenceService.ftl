<#-- Template for generating FetchPersistenceService class -->
package ${basePackage}.useCases.crud.fetch.infrastructure.persistence.service;

import ${basePackage}.domain.model.${modelName}DomainModel;
import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDAdapter;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.model.DomainPersistenceModelAdapter;
import de.gupta.clean.crud.template.useCases.crud.fetch.application.service.FetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.AbstractFetchPersistenceService;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${modelName}FetchPersistenceService
		extends
		AbstractFetchPersistenceService${"<"}Long, ${modelName}DomainModel,
		UUID, ${modelName}PersistenceModel${">"}
		implements
		FetchPersistenceService${"<"}Long, ${modelName}DomainModel${">"}
{
	${modelName}FetchPersistenceService(
			final FetchPersistenceModelRepository${"<"}${modelName}PersistenceModel, UUID${">"} repository,
			final DomainPersistenceModelAdapter${"<"}${modelName}DomainModel,
			${modelName}PersistenceModel${">"} modelAdapter,
			@Qualifier("${modelName?uncap_first}DomainPersistenceIDAdapter") final
			DomainPersistenceIDAdapter${"<"}Long, UUID${">"} idAdapter)
	{
		super(repository, modelAdapter, idAdapter);
	}
}