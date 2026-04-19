<#-- Template for generating PersistenceModelHistory class -->
package ${basePackage()}.infrastructure.persistence.repository;

<#if jpaConverterProperties()?has_content>
import ${basePackage()}.infrastructure.persistence.converter.${persistenceJpaConvertersTypeName()};
</#if>
import ${basePackage()}.infrastructure.persistence.model.${modelBaseName()}PersistenceModel;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.AbstractTriTemporalHistoryModel;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.TemporalChangeType;
import de.gupta.clean.crud.template.infrastructure.persistence.history.model.TriTemporalHistoryModel;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
<#if persistenceModelImports()?has_content>
<#list persistenceModelImports() as import>
<#if import != "java.util.Optional" && import != "java.util.UUID">
import ${import};
</#if>
</#list>
</#if>

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "${persistenceModelHistoryTableName()}",
		indexes = {
				@Index(name = "${modelBaseName()?lower_case}_persistence_history_idx_entity_id", columnList = "entity_id"),
				@Index(name = "${modelBaseName()?lower_case}_persistence_history_idx_validity", columnList = "valid_from, valid_to")
		})
public class ${modelBaseName()}PersistenceModelHistory extends AbstractTriTemporalHistoryModel<UUID>
		implements TriTemporalHistoryModel<UUID>
{
<#list standaloneProperties() as property>
	<#if requiresJpaConverter(property)>
	@Convert(converter = ${persistenceJpaConvertersTypeName()}.${jpaConverterNestedClassName(property)}.class)
	</#if>
	@Column(name = "${sqlColumnName(property)}")
	private ${persistenceResolvedType(property.baseType())} ${property.name()};

</#list>
	static ${modelBaseName()}PersistenceModelHistory snapshotOf(
			final ${modelBaseName()}PersistenceModel model,
			final TemporalChangeType changeType,
			final Instant decisionTime,
			final Instant validFrom,
			final Instant validTo)
	{
		var snapshot = new ${modelBaseName()}PersistenceModelHistory();
		snapshot.setEntityID(model.id());
		snapshot.setChangeType(changeType);
		snapshot.setDecisionTime(decisionTime);
		snapshot.setValidFrom(validFrom);
		snapshot.setValidTo(validTo);
<#list standaloneProperties() as property>
		snapshot.${property.name()} = <#if property.optional()>model.${property.name()}().orElse(null)<#else>model.${property.name()}()</#if>;
</#list>
		return snapshot;
	}

	protected ${modelBaseName()}PersistenceModelHistory()
	{
		super();
	}
}
