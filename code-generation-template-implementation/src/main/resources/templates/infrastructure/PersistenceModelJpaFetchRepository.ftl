<#-- Template for generating PersistenceModelJpaFetchRepository class -->
package ${aggregate().basePackage()}.useCases.crud.fetch.infrastructure.persistence.repository;

import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}JpaRepository;
import ${aggregate().basePackage()}.infrastructure.persistence.repository.${aggregate().baseName()}PersistenceModelImpl;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.repository.AbstractPersistenceModelJpaFetchRepository;
import de.gupta.clean.crud.template.useCases.crud.fetch.infrastructure.persistence.service.FetchPersistenceModelRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ${aggregate().baseName()}PersistenceModelJpaFetchRepository
		extends AbstractPersistenceModelJpaFetchRepository<${aggregate().baseName()}PersistenceModel, UUID, ${aggregate().baseName()}PersistenceModelImpl>
		implements FetchPersistenceModelRepository<${aggregate().baseName()}PersistenceModel, UUID>
{
	${aggregate().baseName()}PersistenceModelJpaFetchRepository(final ${aggregate().baseName()}JpaRepository jpaRepository)
	{
		super(jpaRepository);
	}
}