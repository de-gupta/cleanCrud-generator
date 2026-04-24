package de.gupta.clean.crud.implementation.examples.person.domain.model;

import de.gupta.clean.crud.template.useCases.crud.aggregate.relationship.ReconciliationStrategy;
import de.gupta.clean.crud.template.generation.specification.AggregateGenerationSpec;
import de.gupta.clean.crud.template.generation.specification.AggregateGenerationSpecs;
import de.gupta.clean.crud.template.generation.specification.CodeGenerationSpecification;
import de.gupta.clean.crud.template.generation.specification.Relationship;
import de.gupta.clean.crud.implementation.examples.note.domain.model.NoteModel;
import de.gupta.clean.crud.implementation.examples.version.domain.model.VersionModel;

import java.util.UUID;

public final class PersonGenerationSpec implements CodeGenerationSpecification
{
	@Override
	public AggregateGenerationSpec specification()
	{
		return AggregateGenerationSpecs.aggregate(PersonModel.class)
				.rootApiIdType(Long.class)
				.rootDomainIdType(Long.class)
				.rootPersistenceIdType(UUID.class)
				.relationship(Relationship.owned("notes", NoteModel.class)
						.apiIdType(Long.class)
						.domainIdType(Long.class)
						.persistenceIdType(UUID.class)
						.reconciliationStrategy(ReconciliationStrategy.MERGE_BY_ID))
				.relationship(Relationship.referenced("currentVersion", VersionModel.class)
						.apiIdType(Long.class)
						.domainIdType(Long.class)
						.persistenceIdType(UUID.class))
				.relationship(Relationship.referenced("lastKnownVersion", VersionModel.class)
						.apiIdType(Long.class)
						.domainIdType(Long.class)
						.persistenceIdType(UUID.class))
				.build();
	}
}


