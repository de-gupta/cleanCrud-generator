<#-- Template for generating PersistenceModelHistory class -->
package ${aggregate().basePackage()}.infrastructure.persistence.repository;

<#if persistence().converterProperties()?has_content>
import ${aggregate().basePackage()}.infrastructure.persistence.converter.${persistence().jpaConvertersTypeName()};
</#if>
import ${aggregate().basePackage()}.infrastructure.persistence.model.${aggregate().baseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.AbstractTriTemporalHistoryModel;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.TemporalChangeType;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.TriTemporalHistoryModel;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
<#if persistence().imports()?has_content>
<#list persistence().imports() as import>
<#if import != "java.util.Optional" && import != "java.util.UUID">
import ${import};
</#if>
</#list>
</#if>

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "${persistence().historyTableName()}",
		indexes = {
				@Index(name = "${aggregate().baseName()?lower_case}_persistence_history_idx_entity_id", columnList = "entity_id"),
				@Index(name = "${aggregate().baseName()?lower_case}_persistence_history_idx_validity", columnList = "valid_from, valid_to")
		})
public class ${aggregate().baseName()}PersistenceModelHistory extends AbstractTriTemporalHistoryModel<UUID>
		implements TriTemporalHistoryModel<UUID>
{
<#list composition().standaloneProperties() as property>
	<#if persistence().requiresJpaConverter(property)>
	@Convert(converter = ${persistence().jpaConvertersTypeName()}.${persistence().converterNestedClassName(property)}.class)
	</#if>
	@Column(name = "${persistence().sqlColumnName(property)}")
	private ${persistence().resolvedType(property.baseType())} ${property.name()};

</#list>
	static ${aggregate().baseName()}PersistenceModelHistory snapshotOf(
			final ${aggregate().baseName()}PersistenceModel model,
			final TemporalChangeType changeType,
			final Instant decisionTime,
			final Instant validFrom,
			final Instant validTo)
	{
		var snapshot = new ${aggregate().baseName()}PersistenceModelHistory();
		snapshot.setEntityID(model.id());
		snapshot.setChangeType(changeType);
		snapshot.setDecisionTime(decisionTime);
		snapshot.setValidFrom(validFrom);
		snapshot.setValidTo(validTo);
<#list composition().standaloneProperties() as property>
		snapshot.${property.name()} = <#if property.optional()>model.${property.name()}().orElse(null)<#else>model.${property.name()}()</#if>;
</#list>
		return snapshot;
	}

	protected ${aggregate().baseName()}PersistenceModelHistory()
	{
		super();
	}
}
