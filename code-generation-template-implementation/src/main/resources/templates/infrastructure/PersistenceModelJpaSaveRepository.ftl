<#-- Template for generating PersistenceModelJpaSaveRepository class -->
package ${aggregate().basePackage()}.useCases.crud.save.infrastructure.persistence.repository;

import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}JpaRepository;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}PersistenceModelHistory;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}PersistenceModelImpl;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.TriTemporalHistorySnapshotFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.history.service.AuditActorSupplier;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.repository.AbstractPersistenceModelJpaSaveRepository;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.SavePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ${aggregate().baseName()}PersistenceModelJpaSaveRepository
		extends AbstractPersistenceModelJpaSaveRepository<${aggregate().baseName()}PersistenceModel, UUID, ${aggregate().baseName()}PersistenceModelImpl,
		${aggregate().baseName()}PersistenceModelHistory>
		implements SavePersistenceModelRepository<${aggregate().baseName()}PersistenceModel>
{
	${aggregate().baseName()}PersistenceModelJpaSaveRepository(
			final ${aggregate().baseName()}JpaRepository jpaRepository,
			final TriTemporalHistoryRepository<UUID, ${aggregate().baseName()}PersistenceModelHistory> historyRepository,
			final TriTemporalHistorySnapshotFactory<UUID, ${aggregate().baseName()}PersistenceModel, ${aggregate().baseName()}PersistenceModelHistory> snapshotFactory,
			@Qualifier("${aggregate().beanNamePrefix()}AuditActorSupplier") final AuditActorSupplier auditActorSupplier)
	{
		super(jpaRepository, historyRepository, snapshotFactory, auditActorSupplier);
	}
}
