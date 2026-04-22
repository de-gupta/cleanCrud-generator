<#-- Template for generating PersistenceModelJpaUpdateRepository class -->
package ${aggregate().basePackage()}.useCases.crud.update.infrastructure.persistence.repository;

import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}JpaRepository;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}PersistenceHistorySnapshotFactory;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}PersistenceModelHistory;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}PersistenceModelImpl;
import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.history.service.AuditActorSupplier;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.repository.AbstractPersistenceModelJpaUpdateRepository;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.service.UpdatePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ${aggregate().baseName()}PersistenceModelJpaUpdateRepository
		extends AbstractPersistenceModelJpaUpdateRepository<${aggregate().baseName()}PersistenceModel, UUID, ${aggregate().baseName()}PersistenceModelImpl,
		${aggregate().baseName()}PersistenceModelHistory>
		implements UpdatePersistenceModelRepository<${aggregate().baseName()}PersistenceModel>
{
	public ${aggregate().baseName()}PersistenceModelJpaUpdateRepository(
			final ${aggregate().baseName()}JpaRepository jpaRepository,
			final TriTemporalHistoryRepository<UUID, ${aggregate().baseName()}PersistenceModelHistory> historyRepository,
			final ${aggregate().baseName()}PersistenceHistorySnapshotFactory snapshotFactory,
			@Qualifier("${aggregate().beanNamePrefix()}AuditActorSupplier") final AuditActorSupplier auditActorSupplier)
	{
		super(jpaRepository, historyRepository, snapshotFactory, auditActorSupplier);
	}
}
