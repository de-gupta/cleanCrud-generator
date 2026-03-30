<#-- Template for generating DomainPersistenceAdapterModel class -->
package ${basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.AbstractDomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.DomainPersistenceAdapterModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.UUID;

@Entity
@Table(name = "${domainPersistenceAdapterTableName()}",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "domain_id"),
				@UniqueConstraint(columnNames = "persistence_id")
		},
		indexes = {
				@Index(name = "${modelBaseName()?lower_case}_idx_domain_id", columnList = "domain_id"),
				@Index(name = "${modelBaseName()?lower_case}_idx_persistence_id", columnList = "persistence_id")
		})
public class ${modelBaseName()}DomainPersistenceAdapterModel extends AbstractDomainPersistenceAdapterModel<Long, UUID>
		implements DomainPersistenceAdapterModel<Long, UUID>
{
	public static DomainPersistenceAdapterModel.Builder<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterModel> builder()
	{
		return new Builder();
	}

	protected ${modelBaseName()}DomainPersistenceAdapterModel()
	{
		super();
	}

	private static final class Builder extends AbstractBuilder<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterModel>
			implements DomainPersistenceAdapterModel.Builder<Long, UUID, ${modelBaseName()}DomainPersistenceAdapterModel>,
			de.gupta.clean.crud.template.domain.model.builder.ModelBuilder<${modelBaseName()}DomainPersistenceAdapterModel>
	{
		@Override
		protected ${modelBaseName()}DomainPersistenceAdapterModel doBuild()
		{
			return (${modelBaseName()}DomainPersistenceAdapterModel) model;
		}

		private Builder()
		{
			super(new ${modelBaseName()}DomainPersistenceAdapterModel());
		}
	}
}