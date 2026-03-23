<#-- Template for generating DomainPersistenceIDManagement class -->
package ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.adapter;

import ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelName}DomainPersistenceAdapterHistoryJpaRepository;
import ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelName}DomainPersistenceAdapterHistoryModel;
import ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelName}DomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.domain.model.builder.BuilderFactories;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.AbstractDomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.DomainPersistenceAdapterRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.service.DomainIDGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${modelName}DomainPersistenceIDManagement
		extends AbstractDomainPersistenceIDManagement<Long, UUID, ${modelName}DomainPersistenceAdapterModel,
		${modelName}DomainPersistenceAdapterHistoryModel>
		implements DomainPersistenceIDManagement<Long, UUID>
{
	${modelName}DomainPersistenceIDManagement(
			final DomainPersistenceAdapterRepository<Long, UUID, ${modelName}DomainPersistenceAdapterModel> repository,
			final ${modelName}DomainPersistenceAdapterHistoryJpaRepository historyRepository,
			@Qualifier("${modelName?uncap_first}LongDomainIDGenerator") final DomainIDGenerator<Long> domainIDGenerator)
	{
		super(repository,
				BuilderFactories.of(${modelName}DomainPersistenceAdapterModel::builder),
				historyRepository,
				BuilderFactories.of(${modelName}DomainPersistenceAdapterHistoryModel::builder),
				domainIDGenerator);
	}
}