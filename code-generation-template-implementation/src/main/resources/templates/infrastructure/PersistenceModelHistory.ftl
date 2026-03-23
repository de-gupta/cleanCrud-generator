<#-- Template for generating PersistenceModelHistory class -->
package ${basePackage}.infrastructure.persistence.repository;

import ${basePackage}.infrastructure.persistence.model.${modelName}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.AbstractTriTemporalHistoryModel;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.TemporalChangeType;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.TriTemporalHistoryModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
<#list properties() as property>
<#if property.baseType()Import()?has_content>
import ${property.baseType()Import()};
</#if>
</#list>

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "${modelName?lower_case}_persistence_model_history",
		indexes = {
				@Index(name = "${modelName?lower_case}_persistence_history_idx_entity_id", columnList = "entity_id"),
				@Index(name = "${modelName?lower_case}_persistence_history_idx_validity", columnList = "valid_from, valid_to")
		})
public class ${modelName}PersistenceModelHistory extends AbstractTriTemporalHistoryModel<UUID>
		implements TriTemporalHistoryModel<UUID>
{
<#list properties() as property>
	@Column
	private ${property.baseType()} ${property.name()};

</#list>
	static ${modelName}PersistenceModelHistory snapshotOf(
			final ${modelName}PersistenceModel model,
			final TemporalChangeType changeType,
			final Instant decisionTime,
			final Instant validFrom,
			final Instant validTo)
	{
		var snapshot = new ${modelName}PersistenceModelHistory();
		snapshot.setEntityID(model.id());
		snapshot.setChangeType(changeType);
		snapshot.setDecisionTime(decisionTime);
		snapshot.setValidFrom(validFrom);
		snapshot.setValidTo(validTo);
<#list properties() as property>
		snapshot.${property.name()} = <#if property.optional()>model.${property.name()}().orElse(null)<#else>model.${property.name()}()</#if>;
</#list>
		return snapshot;
	}

	protected ${modelName}PersistenceModelHistory()
	{
		super();
	}
}