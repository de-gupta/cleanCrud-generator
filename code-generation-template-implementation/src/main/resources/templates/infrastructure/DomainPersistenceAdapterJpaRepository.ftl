<#-- Template for generating DomainPersistenceAdapterJpaRepository interface -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ${modelBaseName()}DomainPersistenceAdapterJpaRepository
		extends JpaRepository<${modelBaseName()}DomainPersistenceAdapterModel, UUID>
{
	boolean existsByDomainID(final Long domainID);

	Optional<${modelBaseName()}DomainPersistenceAdapterModel> findOneByDomainID(Long domainID);

	Optional<${modelBaseName()}DomainPersistenceAdapterModel> findOneByPersistenceID(UUID persistenceID);

	Collection<${modelBaseName()}DomainPersistenceAdapterModel> findAllByDomainIDIn(Collection<Long> domainIDs);

	@Query("""
			SELECT m.domainID
			FROM ${modelBaseName()}DomainPersistenceAdapterModel m
			WHERE m.domainID IN :domainIDs
			""")
	Collection<Long> findExistingDomainIDsFrom(@Param("domainIDs") final Collection<Long> domainIDs);
}