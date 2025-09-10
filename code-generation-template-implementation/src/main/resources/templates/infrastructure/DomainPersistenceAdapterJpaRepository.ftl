<#-- Template for generating DomainPersistenceAdapterJpaRepository interface -->
package ${basePackage}.infrastructure.persistence.adapter.persistence.domain.id.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface ${modelName}DomainPersistenceAdapterJpaRepository
extends JpaRepository${"<"}${modelName}DomainPersistenceAdapterModel, Long${">"}
{
boolean existsByDomainID(final Long domainID);

@Query("""
SELECT m.domainID
FROM ${modelName}DomainPersistenceAdapterModel m
WHERE m.domainID IN :domainIDs
"""
)
Collection${"<"}Long${">"} findExistingDomainIDsFrom(@Param("domainIDs") final Collection${"<"}Long${">"} domainIDs);

Collection${"<"}${modelName}DomainPersistenceAdapterModel${">"} findAllByDomainIDAndValidFromIsBeforeAndValidToIsAfter(
final Long domainID, final Instant validFrom, final Instant validTo);

Collection${"<"}${modelName}DomainPersistenceAdapterModel${">"} findAllByPersistenceIDAndValidFromIsBeforeAndValidToIsAfter(
final UUID persistenceID, final Instant validFrom, final Instant validTo);
}