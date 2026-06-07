package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.*;
import de.gupta.clean.crud.template.domain.relationship.Relationship;
import de.gupta.clean.crud.template.domain.relationship.Relationships;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GenerationContextResolverTest
{
	@Test
	void returnsParsedModelAndOriginalConfigurationWhenNoSpecPathIsConfigured()
	{
		Model model = model("TaskModel", "V");
		DomainModelParser parser = ignored -> model;
		GenerationSpecificationLoader loader = (ignoredBaseModel, ignoredSpecPath) ->
		{
			throw new AssertionError("Loader should not be called when no spec path is configured");
		};
		var resolver =
				new GenerationContextResolver(parser, loader, new GenerationSpecificationConfigurationAssembler());
		var configuration = new CodeGenerationConfiguration(
				new GenerationInputs("TaskModel.java", null, null, null, null),
				LayerConcreteTypes.defaults(),
				GenerationSelection.defaults(),
				List.of(),
				RootAggregateIdConfiguration.defaults(),
				OwnershipConfiguration.defaults(),
				OverwriteConfiguration.defaults(),
				false);

		ResolvedGenerationRequest resolved = resolver.resolve(configuration);

		assertSame(model, resolved.model());
		assertSame(configuration, resolved.configuration());
	}

	@Test
	void overlaysRelationshipsFromRelationshipsDeclaration()
	{
		Model model = model("TaskModel", "V");
		DomainModelParser parser = ignored -> model;
		GenerationSpecificationLoader loader = (ignoredBaseModel, ignoredSpecPath) -> new Relationships()
		{
			@Override
			public Class<?> baseModelClass()
			{
				return TaskModel.class;
			}

			@Override
			public java.util.Collection<Relationship> relationships()
			{
				return List.of(Relationship.owned("version", VersionModel.class)
				                           .satelliteApiIdType(Long.class)
				                           .satelliteDomainIdType(Long.class)
				                           .satellitePersistenceIdType(java.util.UUID.class)
				                           .build());
			}
		};
		var resolver =
				new GenerationContextResolver(parser, loader, new GenerationSpecificationConfigurationAssembler());
		var configuration = new CodeGenerationConfiguration(
				new GenerationInputs("TaskModel.java", null, null, null, "TaskRelationships.java"),
				LayerConcreteTypes.defaults(),
				GenerationSelection.defaults(),
				List.of(),
				new RootAggregateIdConfiguration("java.lang.String", "java.lang.Long", "java.util.UUID"),
				OwnershipConfiguration.defaults(),
				OverwriteConfiguration.defaults(),
				false);

		ResolvedGenerationRequest resolved = resolver.resolve(configuration);
		var relationship = resolved.configuration().relationships().getFirst();

		assertSame(model, resolved.model());
		assertEquals("java.lang.String", resolved.configuration().rootAggregateIds().apiIdType());
		assertEquals(1, resolved.configuration().relationships().size());
		assertEquals("version", relationship.masterProperty());
		assertEquals("Version", relationship.satelliteAggregate());
	}

	@Test
	void rejectsRelationshipsThatTargetADifferentBaseModel()
	{
		Model model = model("TaskModel", "V");
		DomainModelParser parser = ignored -> model;
		GenerationSpecificationLoader loader = (ignoredBaseModel, ignoredSpecPath) -> new Relationships()
		{
			@Override
			public Class<?> baseModelClass()
			{
				return WorkspaceModel.class;
			}

			@Override
			public java.util.Collection<Relationship> relationships()
			{
				return List.of(Relationship.referenced("organisation", OrganisationModel.class)
				                           .satelliteApiIdType(Long.class)
				                           .satelliteDomainIdType(Long.class)
				                           .satellitePersistenceIdType(java.util.UUID.class)
				                           .build());
			}
		};
		var resolver =
				new GenerationContextResolver(parser, loader, new GenerationSpecificationConfigurationAssembler());
		var configuration = new CodeGenerationConfiguration(
				new GenerationInputs("TaskModel.java", null, null, null, "WorkspaceRelationships.java"),
				LayerConcreteTypes.defaults(),
				GenerationSelection.defaults(),
				List.of(),
				RootAggregateIdConfiguration.defaults(),
				OwnershipConfiguration.defaults(),
				OverwriteConfiguration.defaults(),
				false);

		var error = assertThrows(IllegalArgumentException.class, () -> resolver.resolve(configuration));

		assertEquals(
				"Relationships declaration targets base model `WorkspaceModel` but the configured base model source is `TaskModel`",
				error.getMessage());
	}

	private static Model model(final String modelName, final String genericPlaceholder)
	{
		return Model.of(
				modelName,
				"example.task",
				Path.of("src/main/java"),
				List.of(genericPlaceholder),
				Set.of(Property.of("version", "Optional<" + genericPlaceholder + ">",
						"java.util.Optional<" + genericPlaceholder + ">")));
	}

	interface TaskModel<V>
	{
	}

	interface VersionModel
	{
	}

	interface WorkspaceModel<O>
	{
	}

	interface OrganisationModel
	{
	}
}