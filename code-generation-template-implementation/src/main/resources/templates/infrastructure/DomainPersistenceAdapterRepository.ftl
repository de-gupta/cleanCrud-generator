<#-- Template for generating DomainPersistenceAdapterRepository class -->
package ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.AbstractDomainPersistenceAdapterJpaRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.DomainPersistenceAdapterRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Component
@Qualifier("${modelName?uncap_first}DomainPersistenceAdapterRepository")
final class ${modelName}DomainPersistenceAdapterRepository
		extends AbstractDomainPersistenceAdapterJpaRepository<Long, UUID, ${modelName}DomainPersistenceAdapterModel>
		implements DomainPersistenceAdapterRepository<Long, UUID, ${modelName}DomainPersistenceAdapterModel>
{
	private final ${modelName}DomainPersistenceAdapterJpaRepository jpaRepository;

	@Override
	public boolean existsByDomainID(final Long domainID)
	{
		return jpaRepository.existsByDomainID(domainID);
	}

	@Override
	public Collection<Long> existingDomainIDsFrom(final Collection<Long> domainIDs)
	{
		return jpaRepository.findExistingDomainIDsFrom(domainIDs);
	}

	@Override
	protected Optional<${modelName}DomainPersistenceAdapterModel> findOneByPersistenceID(final UUID uuid)
	{
		return jpaRepository.findOneByPersistenceID(uuid);
	}

	@Override
	protected Optional<${modelName}DomainPersistenceAdapterModel> findOneByDomainID(final Long domainID)
	{
		return jpaRepository.findOneByDomainID(domainID);
	}

	@Override
	protected Collection<${modelName}DomainPersistenceAdapterModel> findAllByDomainIDIn(final Collection<Long> domainIDs)
	{
		return jpaRepository.findAllByDomainIDIn(domainIDs);
	}

	${modelName}DomainPersistenceAdapterRepository(final ${modelName}DomainPersistenceAdapterJpaRepository jpaRepository)
	{
		super(jpaRepository);
		this.jpaRepository = jpaRepository;
	}
}