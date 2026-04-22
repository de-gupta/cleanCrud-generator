<#-- Template for generating DomainPersistenceIDManagement class -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.adapter;

import ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model.${aggregate().baseName()}DomainPersistenceAdapterHistoryModel;
import ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model.${aggregate().baseName()}DomainPersistenceAdapterModel;
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
final class ${aggregate().baseName()}DomainPersistenceIDManagement
		extends AbstractDomainPersistenceIDManagement<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel,
		${aggregate().baseName()}DomainPersistenceAdapterHistoryModel>
		implements DomainPersistenceIDManagement<Long, UUID>
{
	${aggregate().baseName()}DomainPersistenceIDManagement(
			final DomainPersistenceAdapterRepository<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel> repository,
			final TriTemporalHistoryRepository<Long, ${aggregate().baseName()}DomainPersistenceAdapterHistoryModel> historyRepository,
			@Qualifier("${aggregate().beanNamePrefix()}LongDomainIDGenerator") final DomainIDGenerator<Long> domainIDGenerator,
			@Qualifier("${aggregate().beanNamePrefix()}AuditActorSupplier") final AuditActorSupplier auditActorSupplier)
	{
		super(repository,
				BuilderFactories.of(${aggregate().baseName()}DomainPersistenceAdapterModel::builder),
				historyRepository,
				BuilderFactories.of(${aggregate().baseName()}DomainPersistenceAdapterHistoryModel::builder),
				domainIDGenerator, auditActorSupplier);
	}
}
