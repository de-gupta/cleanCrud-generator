<#-- Template for generating PersistenceModelJpaUpdateRepository class -->
package ${basePackage}.useCases.crud.update.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import ${basePackage}.infrastructure.persistence.repository.${modelName}JpaRepository;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceHistorySnapshotFactory;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelHistory;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelHistoryJpaRepository;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelImpl;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.repository.AbstractPersistenceModelJpaUpdateRepository;
import de.gupta.clean.crud.template.useCases.crud.update.infrastructure.persistence.service.UpdatePersistenceModelRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ${modelName}PersistenceModelJpaUpdateRepository
		extends AbstractPersistenceModelJpaUpdateRepository<${modelName}PersistenceModel, UUID, ${modelName}PersistenceModelImpl,
		${modelName}PersistenceModelHistory>
		implements UpdatePersistenceModelRepository<${modelName}PersistenceModel>
{
	public ${modelName}PersistenceModelJpaUpdateRepository(
			final ${modelName}JpaRepository jpaRepository,
			final ${modelName}PersistenceModelHistoryJpaRepository historyRepository,
			final ${modelName}PersistenceHistorySnapshotFactory snapshotFactory)
	{
		super(jpaRepository, historyRepository, snapshotFactory);
	}
}