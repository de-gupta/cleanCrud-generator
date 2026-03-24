<#-- Template for generating PersistenceModelJpaFetchRepository class -->
package ${basePackage()}.useCases.crud.fetch.infrastructure.persistence.repository;

import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}JpaRepository;
import ${basePackage()}.infrastructure.persistence.repository.${modelBaseName()}PersistenceModelImpl;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.repository.AbstractPersistenceModelJpaFetchRepository;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ${modelBaseName()}PersistenceModelJpaFetchRepository
		extends AbstractPersistenceModelJpaFetchRepository<${modelBaseName()}PersistenceModel, UUID, ${modelBaseName()}PersistenceModelImpl>
		implements FetchPersistenceModelRepository<${modelBaseName()}PersistenceModel, UUID>
{
	${modelBaseName()}PersistenceModelJpaFetchRepository(final ${modelBaseName()}JpaRepository jpaRepository)
	{
		super(jpaRepository);
	}
}