<#-- Template for generating PersistenceModelJpaUpdateRepository class -->
package ${basePackage()}.useCases.crud.update.infrastructure.persistence.repository;

import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}JpaRepository;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceHistorySnapshotFactory;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelHistory;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelHistoryRepository;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelImpl;
import de.gupta.clean.crud.template.infrastructure.persistence.history.service.AuditActorSupplier;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.repository.AbstractPersistenceModelJpaUpdateRepository;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.service.UpdatePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ${modelBaseName()}PersistenceModelJpaUpdateRepository
		extends AbstractPersistenceModelJpaUpdateRepository<${modelBaseName()}PersistenceModel, UUID, ${modelBaseName()}PersistenceModelImpl,
		${modelBaseName()}PersistenceModelHistory>
		implements UpdatePersistenceModelRepository<${modelBaseName()}PersistenceModel>
{
	public ${modelBaseName()}PersistenceModelJpaUpdateRepository(
			final ${modelBaseName()}JpaRepository jpaRepository,
			final ${modelBaseName()}PersistenceModelHistoryRepository historyRepository,
			final ${modelBaseName()}PersistenceHistorySnapshotFactory snapshotFactory,
			@Qualifier("${beanNamePrefix()}AuditActorSupplier") final AuditActorSupplier auditActorSupplier)
	{
		super(jpaRepository, historyRepository, snapshotFactory, auditActorSupplier);
	}
}
