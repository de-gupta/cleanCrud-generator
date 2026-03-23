<#-- Template for generating PersistenceModelJpaDeleteRepository class -->
package ${basePackage}.useCases.crud.delete.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import ${basePackage}.infrastructure.persistence.repository.${modelName}JpaRepository;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelHistory;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelHistoryJpaRepository;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelImpl;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.TriTemporalHistorySnapshotFactory;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.repository.AbstractPersistenceModelJpaDeleteRepository;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.service.DeletePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Qualifier("${modelName?uncap_first}DeletePersistenceModelRepository")
class ${modelName}PersistenceModelJpaDeleteRepository
		extends AbstractPersistenceModelJpaDeleteRepository<${modelName}PersistenceModel, UUID, ${modelName}PersistenceModelImpl,
		${modelName}PersistenceModelHistory>
		implements DeletePersistenceModelRepository<UUID>
{
	${modelName}PersistenceModelJpaDeleteRepository(
			final ${modelName}JpaRepository jpaRepository,
			final ${modelName}PersistenceModelHistoryJpaRepository historyRepository,
			final TriTemporalHistorySnapshotFactory<UUID, ${modelName}PersistenceModel, ${modelName}PersistenceModelHistory> snapshotFactory)
	{
		super(jpaRepository, historyRepository, snapshotFactory);
	}
}