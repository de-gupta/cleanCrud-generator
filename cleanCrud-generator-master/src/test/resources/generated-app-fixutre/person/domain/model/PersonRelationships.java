package de.gupta.clean.crud.implementation.examples.person.domain.model;

import de.gupta.clean.crud.implementation.examples.note.domain.model.NoteModel;
import de.gupta.clean.crud.implementation.examples.version.domain.model.VersionModel;
import de.gupta.clean.crud.template.domain.relationship.Relationship;
import de.gupta.clean.crud.template.domain.relationship.Relationships;
import de.gupta.clean.crud.template.domain.relationship.ReconciliationStrategy;

import java.util.List;

public final class PersonRelationships implements Relationships
{
	@Override
	public Class<?> baseModelClass()
	{
		return PersonModel.class;
	}

	@Override
	public List<Relationship> relationships()
	{
		return List.of(
				Relationship.owned("notes", NoteModel.class)
				            .satelliteApiIdType(Long.class)
				            .satelliteDomainIdType(Long.class)
				            .satellitePersistenceIdType(java.util.UUID.class)
				            .reconciliationStrategy(ReconciliationStrategy.MERGE_BY_ID)
				            .build(),
				Relationship.referenced("currentVersion", VersionModel.class)
				            .satelliteApiIdType(Long.class)
				            .satelliteDomainIdType(Long.class)
				            .satellitePersistenceIdType(java.util.UUID.class)
				            .build(),
				Relationship.referenced("lastKnownVersion", VersionModel.class)
				            .satelliteApiIdType(Long.class)
				            .satelliteDomainIdType(Long.class)
				            .satellitePersistenceIdType(java.util.UUID.class)
				            .build());
	}
}
