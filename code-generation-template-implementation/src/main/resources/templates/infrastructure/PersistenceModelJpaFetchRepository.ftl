<#-- Template for generating PersistenceModelJpaFetchRepository class -->
package ${basePackage}.useCases.crud.fetch.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import ${basePackage}.infrastructure.persistence.repository.${modelName}JpaRepository;
import ${basePackage}.infrastructure.persistence.repository.${modelName}PersistenceModelImpl;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.repository.AbstractPersistenceModelJpaFetchRepository;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ${modelName}PersistenceModelJpaFetchRepository
		extends AbstractPersistenceModelJpaFetchRepository<${modelName}PersistenceModel, UUID, ${modelName}PersistenceModelImpl>
		implements FetchPersistenceModelRepository<${modelName}PersistenceModel, UUID>
{
	${modelName}PersistenceModelJpaFetchRepository(final ${modelName}JpaRepository jpaRepository)
	{
		super(jpaRepository);
	}
}