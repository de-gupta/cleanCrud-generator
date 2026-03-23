<#-- Template for generating PersistenceHistorySnapshotFactory class -->
package ${basePackage}.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.AbstractPersistenceHistorySnapshotFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.history.adapter.TriTemporalHistorySnapshotFactory;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.TemporalChangeType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public final class ${modelName}PersistenceHistorySnapshotFactory
		extends AbstractPersistenceHistorySnapshotFactory<UUID, ${modelName}PersistenceModel, ${modelName}PersistenceModelHistory>
		implements TriTemporalHistorySnapshotFactory<UUID, ${modelName}PersistenceModel, ${modelName}PersistenceModelHistory>
{
	@Override
	public ${modelName}PersistenceModelHistory snapshotOf(
			final ${modelName}PersistenceModel model,
			final TemporalChangeType changeType,
			final Instant decisionTime,
			final Instant validFrom,
			final Instant validTo)
	{
		return ${modelName}PersistenceModelHistory.snapshotOf(model, changeType, decisionTime, validFrom, validTo);
	}
}