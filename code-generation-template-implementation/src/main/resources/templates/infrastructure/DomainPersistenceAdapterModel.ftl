<#-- Template for generating DomainPersistenceAdapterModel class -->
package ${aggregate().basePackage()}.infrastructure.persistence.adapter.persistence.domain.id.model;

import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.AbstractDomainPersistenceAdapterModel;
import de.gupta.clean.crud.template.infrastructure.persistence.adapter.persistence.domain.id.model.DomainPersistenceAdapterModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.UUID;

@Entity
@Table(name = "${persistence().adapterTableName()}",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "domain_id"),
				@UniqueConstraint(columnNames = "persistence_id")
		},
		indexes = {
				@Index(name = "${aggregate().baseName()?lower_case}_idx_domain_id", columnList = "domain_id"),
				@Index(name = "${aggregate().baseName()?lower_case}_idx_persistence_id", columnList = "persistence_id")
		})
public class ${aggregate().baseName()}DomainPersistenceAdapterModel extends AbstractDomainPersistenceAdapterModel<Long, UUID>
		implements DomainPersistenceAdapterModel<Long, UUID>
{
	public static DomainPersistenceAdapterModel.Builder<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel> builder()
	{
		return new Builder();
	}

	protected ${aggregate().baseName()}DomainPersistenceAdapterModel()
	{
		super();
	}

	private static final class Builder extends AbstractBuilder<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel>
			implements DomainPersistenceAdapterModel.Builder<Long, UUID, ${aggregate().baseName()}DomainPersistenceAdapterModel>,
			de.gupta.clean.crud.template.domain.model.builder.ModelBuilder<${aggregate().baseName()}DomainPersistenceAdapterModel>
	{
		@Override
		protected ${aggregate().baseName()}DomainPersistenceAdapterModel doBuild()
		{
			return (${aggregate().baseName()}DomainPersistenceAdapterModel) model;
		}

		private Builder()
		{
			super(new ${aggregate().baseName()}DomainPersistenceAdapterModel());
		}
	}
}