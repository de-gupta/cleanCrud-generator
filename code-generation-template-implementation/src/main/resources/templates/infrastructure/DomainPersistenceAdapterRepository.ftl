<#-- Template for generating DomainPersistenceAdapterRepository class -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.AbstractDomainPersistenceAdapterJpaRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.DomainPersistenceAdapterRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Component
@Qualifier("${modelName()?uncap_first}DomainPersistenceAdapterRepository")
final class ${modelBaseName()}DomainPersistenceAdapterRepository
		extends AbstractDomainPersistenceAdapterJpaRepository<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterModel>
		implements DomainPersistenceAdapterRepository<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterModel>
{
	private final ${modelBaseName()}DomainPersistenceAdapterJpaRepository jpaRepository;

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
	protected Optional<${modelBaseName()}DomainPersistenceAdapterModel> findOneByPersistenceID(final UUID uuid)
	{
		return jpaRepository.findOneByPersistenceID(uuid);
	}

	@Override
	protected Optional<${modelBaseName()}DomainPersistenceAdapterModel> findOneByDomainID(final Long domainID)
	{
		return jpaRepository.findOneByDomainID(domainID);
	}

	@Override
	protected Collection<${modelBaseName()}DomainPersistenceAdapterModel> findAllByDomainIDIn(final Collection<Long> domainIDs)
	{
		return jpaRepository.findAllByDomainIDIn(domainIDs);
	}

	${modelBaseName()}DomainPersistenceAdapterRepository(final ${modelBaseName()}DomainPersistenceAdapterJpaRepository jpaRepository)
	{
		super(jpaRepository);
		this.jpaRepository = jpaRepository;
	}
}