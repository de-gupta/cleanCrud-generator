<#-- Template for generating DomainPersistenceAdapterRepository class -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.AbstractDomainPersistenceAdapterJpaRepository;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.repository.DomainPersistenceAdapterRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Component
@Qualifier("${aggregate().beanNamePrefix()}DomainPersistenceAdapterRepository")
final class ${aggregate().baseName()}DomainPersistenceAdapterRepository
		extends AbstractDomainPersistenceAdapterJpaRepository<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel>
		implements DomainPersistenceAdapterRepository<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel>
{
	private final ${aggregate().baseName()}DomainPersistenceAdapterJpaRepository jpaRepository;

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
	protected Optional<${aggregate().baseName()}DomainPersistenceAdapterModel> findOneByPersistenceID(final UUID uuid)
	{
		return jpaRepository.findOneByPersistenceID(uuid);
	}

	@Override
	protected Optional<${aggregate().baseName()}DomainPersistenceAdapterModel> findOneByDomainID(final Long domainID)
	{
		return jpaRepository.findOneByDomainID(domainID);
	}

	@Override
	protected Collection<${aggregate().baseName()}DomainPersistenceAdapterModel> findAllByDomainIDIn(final Collection<Long> domainIDs)
	{
		return jpaRepository.findAllByDomainIDIn(domainIDs);
	}

	${aggregate().baseName()}DomainPersistenceAdapterRepository(final ${aggregate().baseName()}DomainPersistenceAdapterJpaRepository jpaRepository)
	{
		super(jpaRepository);
		this.jpaRepository = jpaRepository;
	}
}