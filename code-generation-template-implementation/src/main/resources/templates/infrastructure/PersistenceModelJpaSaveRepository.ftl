<#-- Template for generating PersistenceModelJpaSaveRepository class -->
package ${basePackage()}.useCases.crud.save.infrastructure.persistence.repository;

import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}JpaRepository;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelHistory;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelHistoryRepository;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelImpl;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.TriTemporalHistorySnapshotFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.history.service.AuditActorSupplier;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.repository.AbstractPersistenceModelJpaSaveRepository;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.SavePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ${modelBaseName()}PersistenceModelJpaSaveRepository
		extends AbstractPersistenceModelJpaSaveRepository<${modelBaseName()}PersistenceModel, UUID, ${modelBaseName()}PersistenceModelImpl,
		${modelBaseName()}PersistenceModelHistory>
		implements SavePersistenceModelRepository<${modelBaseName()}PersistenceModel>
{
	${modelBaseName()}PersistenceModelJpaSaveRepository(
			final ${modelBaseName()}JpaRepository jpaRepository,
			final ${modelBaseName()}PersistenceModelHistoryRepository historyRepository,
			final TriTemporalHistorySnapshotFactory<UUID, ${modelBaseName()}PersistenceModel, ${modelBaseName()}PersistenceModelHistory> snapshotFactory,
			@Qualifier("${beanNamePrefix()}AuditActorSupplier") final AuditActorSupplier auditActorSupplier)
	{
		super(jpaRepository, historyRepository, snapshotFactory, auditActorSupplier);
	}
}
