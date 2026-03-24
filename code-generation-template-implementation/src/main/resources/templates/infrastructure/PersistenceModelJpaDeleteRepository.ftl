<#-- Template for generating PersistenceModelJpaDeleteRepository class -->
package ${basePackage()}.useCases.crud.delete.infrastructure.persistence.repository;

import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}JpaRepository;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelHistory;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelHistoryJpaRepository;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelImpl;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.TriTemporalHistorySnapshotFactory;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.repository.AbstractPersistenceModelJpaDeleteRepository;
import de.gupta.clean.crud.template.useCases.crud.delete.infrastructure.persistence.service.DeletePersistenceModelRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Qualifier("${beanNamePrefix()}DeletePersistenceModelRepository")
class ${modelBaseName()}PersistenceModelJpaDeleteRepository
		extends AbstractPersistenceModelJpaDeleteRepository<${modelBaseName()}PersistenceModel, UUID, ${modelBaseName()}PersistenceModelImpl,
		${modelBaseName()}PersistenceModelHistory>
		implements DeletePersistenceModelRepository<UUID>
{
	${modelBaseName()}PersistenceModelJpaDeleteRepository(
			final ${modelBaseName()}JpaRepository jpaRepository,
			final ${modelBaseName()}PersistenceModelHistoryJpaRepository historyRepository,
			final TriTemporalHistorySnapshotFactory<UUID, ${modelBaseName()}PersistenceModel, ${modelBaseName()}PersistenceModelHistory> snapshotFactory)
	{
		super(jpaRepository, historyRepository, snapshotFactory);
	}
}