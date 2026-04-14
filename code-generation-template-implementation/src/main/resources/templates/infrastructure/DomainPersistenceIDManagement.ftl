<#-- Template for generating DomainPersistenceIDManagement class -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.adapter;

import ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelBaseName()}DomainPersistenceAdapterHistoryModel;
import ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model.${modelBaseName()}DomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.domain.model.builder.BuilderFactories;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.AbstractDomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.adapter.DomainPersistenceIDManagement;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.DomainPersistenceAdapterRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.service.DomainIDGenerator;
import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.history.service.AuditActorSupplier;
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
			final TriTemporalHistoryRepository<Long, ${modelBaseName()}DomainPersistenceAdapterHistoryModel> historyRepository,
			@Qualifier("${beanNamePrefix()}LongDomainIDGenerator") final DomainIDGenerator<Long> domainIDGenerator,
			@Qualifier("${beanNamePrefix()}AuditActorSupplier") final AuditActorSupplier auditActorSupplier)
	{
		super(repository,
				BuilderFactories.of(${modelBaseName()}DomainPersistenceAdapterModel::builder),
				historyRepository,
				BuilderFactories.of(${modelBaseName()}DomainPersistenceAdapterHistoryModel::builder),
				domainIDGenerator, auditActorSupplier);
	}
}
