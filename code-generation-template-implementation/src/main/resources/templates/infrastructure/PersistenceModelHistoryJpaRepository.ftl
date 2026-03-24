<#-- Template for generating PersistenceModelHistoryJpaRepository interface -->
package ${basePackage()}.infrastructure.persistence.repository;

import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ${modelBaseName()}PersistenceModelHistoryJpaRepository
		extends TriTemporalHistoryJpaRepository<UUID, ${modelBaseName()}PersistenceModelHistory>
{
}