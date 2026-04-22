<#-- Template for generating DomainPersistenceAdapterJpaRepository interface -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ${aggregate().baseName()}DomainPersistenceAdapterJpaRepository
		extends JpaRepository<${aggregate().baseName()}DomainPersistenceAdapterModel, UUID>
{
	boolean existsByDomainID(final Long domainID);

	Optional<${aggregate().baseName()}DomainPersistenceAdapterModel> findOneByDomainID(Long domainID);

	Optional<${aggregate().baseName()}DomainPersistenceAdapterModel> findOneByPersistenceID(UUID persistenceID);

	Collection<${aggregate().baseName()}DomainPersistenceAdapterModel> findAllByDomainIDIn(Collection<Long> domainIDs);

	@Query("""
			SELECT m.domainID
			FROM ${aggregate().baseName()}DomainPersistenceAdapterModel m
			WHERE m.domainID IN :domainIDs
			""")
	Collection<Long> findExistingDomainIDsFrom(@Param("domainIDs") final Collection<Long> domainIDs);
}