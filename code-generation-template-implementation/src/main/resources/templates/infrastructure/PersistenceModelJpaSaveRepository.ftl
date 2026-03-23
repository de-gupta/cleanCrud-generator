<#-- Template for generating PersistenceModelJpaSaveRepository class -->
package ${basePackage}.useCases.crud.save.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import ${basePackage}.infrastructure.persistence.repository.${modelName}JpaRepository;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelHistory;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelHistoryJpaRepository;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelImpl;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.TriTemporalHistorySnapshotFactory;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.repository.AbstractPersistenceModelJpaSaveRepository;
import de.gupta.clean.crud.template.useCases.crud.save.infrastructure.persistence.service.SavePersistenceModelRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ${modelName}PersistenceModelJpaSaveRepository
		extends AbstractPersistenceModelJpaSaveRepository<${modelName}PersistenceModel, UUID, ${modelName}PersistenceModelImpl,
		${modelName}PersistenceModelHistory>
		implements SavePersistenceModelRepository<${modelName}PersistenceModel>
{
	${modelName}PersistenceModelJpaSaveRepository(
			final ${modelName}JpaRepository jpaRepository,
			final ${modelName}PersistenceModelHistoryJpaRepository historyRepository,
			final TriTemporalHistorySnapshotFactory<UUID, ${modelName}PersistenceModel, ${modelName}PersistenceModelHistory> snapshotFactory)
	{
		super(jpaRepository, historyRepository, snapshotFactory);
	}
}