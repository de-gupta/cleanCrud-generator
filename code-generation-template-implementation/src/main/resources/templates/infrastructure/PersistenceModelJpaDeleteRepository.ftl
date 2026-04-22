<#-- Template for generating PersistenceModelJpaDeleteRepository class -->
package ${aggregate().basePackage()}.useCases.crud.delete.infrastructure.persistence.repository;

import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}JpaRepository;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}PersistenceModelHistory;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}PersistenceModelImpl;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.TriTemporalHistorySnapshotFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.history.service.AuditActorSupplier;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.repository.AbstractPersistenceModelJpaDeleteRepository;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.service.DeletePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Qualifier("${aggregate().beanNamePrefix()}DeletePersistenceModelRepository")
class ${aggregate().baseName()}PersistenceModelJpaDeleteRepository
		extends AbstractPersistenceModelJpaDeleteRepository<${aggregate().baseName()}PersistenceModel, UUID, ${aggregate().baseName()}PersistenceModelImpl,
		${aggregate().baseName()}PersistenceModelHistory>
		implements DeletePersistenceModelRepository<UUID>
{
	${aggregate().baseName()}PersistenceModelJpaDeleteRepository(
			final ${aggregate().baseName()}JpaRepository jpaRepository,
			final TriTemporalHistoryRepository<UUID, ${aggregate().baseName()}PersistenceModelHistory> historyRepository,
			final TriTemporalHistorySnapshotFactory<UUID, ${aggregate().baseName()}PersistenceModel, ${aggregate().baseName()}PersistenceModelHistory> snapshotFactory,
			final @Qualifier("${aggregate().beanNamePrefix()}AuditActorSupplier") AuditActorSupplier auditActorSupplier)
	{
		super(jpaRepository, historyRepository, snapshotFactory, auditActorSupplier);
	}
}
