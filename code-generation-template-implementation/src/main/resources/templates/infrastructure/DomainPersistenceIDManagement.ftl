<#-- Template for generating DomainPersistenceIDManagement class -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.adapter;

import ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelBaseName()}DomainPersistenceAdapterHistoryJpaRepository;
import ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelBaseName()}DomainPersistenceAdapterHistoryModel;
import ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelBaseName()}DomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.domain.model.builder.BuilderFactories;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.AbstractDomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.DomainPersistenceAdapterRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.service.DomainIDGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
final class ${modelBaseName()}DomainPersistenceIDManagement
		extends AbstractDomainPersistenceIDManagement<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterModel,
		${modelBaseName()}DomainPersistenceAdapterHistoryModel>
		implements DomainPersistenceIDManagement<Long, UUID>
{
	${modelBaseName()}DomainPersistenceIDManagement(
			final DomainPersistenceAdapterRepository<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterModel> repository,
			final ${modelBaseName()}DomainPersistenceAdapterHistoryJpaRepository historyRepository,
			@Qualifier("${beanNamePrefix()}LongDomainIDGenerator") final DomainIDGenerator<Long> domainIDGenerator)
	{
		super(repository,
				BuilderFactories.of(${modelBaseName()}DomainPersistenceAdapterModel::builder),
				historyRepository,
				BuilderFactories.of(${modelBaseName()}DomainPersistenceAdapterHistoryModel::builder),
				domainIDGenerator);
	}
}