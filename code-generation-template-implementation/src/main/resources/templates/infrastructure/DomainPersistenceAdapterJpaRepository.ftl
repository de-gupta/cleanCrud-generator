<#-- Template for generating DomainPersistenceAdapterJpaRepository interface -->
package ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ${modelName}DomainPersistenceAdapterJpaRepository
		extends JpaRepository<${modelName}DomainPersistenceAdapterModel, UUID>
{
	boolean existsByDomainID(final Long domainID);

	Optional<${modelName}DomainPersistenceAdapterModel> findOneByDomainID(Long domainID);

	Optional<${modelName}DomainPersistenceAdapterModel> findOneByPersistenceID(UUID persistenceID);

	Collection<${modelName}DomainPersistenceAdapterModel> findAllByDomainIDIn(Collection<Long> domainIDs);

	@Query("""
			SELECT m.domainID
			FROM ${modelName}DomainPersistenceAdapterModel m
			WHERE m.domainID IN :domainIDs
			""")
	Collection<Long> findExistingDomainIDsFrom(@Param("domainIDs") final Collection<Long> domainIDs);
}