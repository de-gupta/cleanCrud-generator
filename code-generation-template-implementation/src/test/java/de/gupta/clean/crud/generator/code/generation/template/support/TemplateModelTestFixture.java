package de.gupta.clean.crud.generator.code.generation.template.support;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Property;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.GeneratedRelationship;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModel;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model.TemplateModelFactory;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class TemplateModelTestFixture
{
	public static TemplateModel createPersonTemplateModel()
	{
		return TemplateModelFactory.create(
				"de.gupta.clean.crud.generator.example.person.domain.model",
				"PersonModel",
				List.of("EXTERNAL_ID"),
				properties(),
				Map.of("EXTERNAL_ID", "UUID"),
				Map.of("EXTERNAL_ID", "Long"),
				Map.of("EXTERNAL_ID", "String"),
				Set.of("java.util.UUID"),
				true,
				relationships(),
				"java.lang.Long",
				"java.lang.Long",
				"java.util.UUID",
				true,
				false,
				true,
				false,
				true,
				false);
	}

	private static Set<Property> properties()
	{
		return new LinkedHashSet<>(List.of(
				Property.of("name", "String", "java.lang.String"),
				Property.of("birthDate", "Optional<LocalDate>", "java.util.Optional<java.time.LocalDate>"),
				Property.of("externalId", "Optional<EXTERNAL_ID>", "java.util.Optional<EXTERNAL_ID>"),
				Property.of(
						"status",
						"Status",
						"de.gupta.clean.crud.generator.example.person.domain.model.Status",
						true),
				Property.of(
						"metadata",
						"MetadataValue",
						"de.gupta.clean.crud.generator.example.person.domain.model.MetadataValue"),
				Property.of(
						"addresses",
						"List<AddressAPIModelResponse>",
						"java.util.List<de.gupta.clean.crud.generator.example.person.useCases.crud.common.dto.AddressAPIModelResponse>"),
				Property.of(
						"manager",
						"Optional<ManagerAPIModelResponse>",
						"java.util.Optional<de.gupta.clean.crud.generator.example.person.useCases.crud.common.dto.ManagerAPIModelResponse>")));
	}

	private static List<GeneratedRelationship> relationships()
	{
		return List.of(
				new GeneratedRelationship(
						Property.of(
								"addresses",
								"List<AddressAPIModelResponse>",
								"java.util.List<de.gupta.clean.crud.generator.example.person.useCases.crud.common.dto.AddressAPIModelResponse>"),
						"A",
						"Person",
						"Address",
						"de.gupta.clean.crud.generator.example.address.domain.model.AddressModel",
						"OWNED",
						"MANY",
						"MERGE_BY_ID",
						"java.lang.Long",
						"java.lang.Long",
						"java.util.UUID",
						true,
						true,
						false,
						false,
						true,
						true,
						true),
				new GeneratedRelationship(
						Property.of(
								"manager",
								"Optional<ManagerAPIModelResponse>",
								"java.util.Optional<de.gupta.clean.crud.generator.example.person.useCases.crud.common.dto.ManagerAPIModelResponse>"),
						"M",
						"Person",
						"Manager",
						"de.gupta.clean.crud.generator.example.manager.domain.model.ManagerModel",
						"REFERENCED",
						"ONE",
						"REPLACE",
						"java.util.UUID",
						"java.util.UUID",
						"java.util.UUID",
						false,
						true,
						false,
						false,
						true,
						false,
						false));
	}

	private TemplateModelTestFixture()
	{
	}
}
