<#-- Template for generating PersistenceModelHistoryJpaRepository interface -->
package ${aggregate().basePackage()}.infrastructure.persistence.repository;

import de.gupta.clean.crud.template.infrastructure.persistence.history.repository.TriTemporalHistoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ${aggregate().baseName()}PersistenceModelHistoryJpaRepository
		extends TriTemporalHistoryJpaRepository<UUID, ${aggregate().baseName()}PersistenceModelHistory>
{
}