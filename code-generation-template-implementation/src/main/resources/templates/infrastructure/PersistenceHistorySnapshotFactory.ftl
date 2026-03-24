<#-- Template for generating PersistenceHistorySnapshotFactory class -->
package ${basePackage()}.infrastructure.persistence.repository;

import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.AbstractPersistenceHistorySnapshotFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.TriTemporalHistorySnapshotFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.TemporalChangeType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public final class ${modelBaseName()}PersistenceHistorySnapshotFactory
		extends AbstractPersistenceHistorySnapshotFactory<UUID, ${modelBaseName()}PersistenceModel, ${modelBaseName()}PersistenceModelHistory>
		implements TriTemporalHistorySnapshotFactory<UUID, ${modelBaseName()}PersistenceModel, ${modelBaseName()}PersistenceModelHistory>
{
	@Override
	public ${modelBaseName()}PersistenceModelHistory snapshotOf(
			final ${modelBaseName()}PersistenceModel model,
			final TemporalChangeType changeType,
			final Instant decisionTime,
			final Instant validFrom,
			final Instant validTo)
	{
		return ${modelBaseName()}PersistenceModelHistory.snapshotOf(model, changeType, decisionTime, validFrom, validTo);
	}
}