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
				relationships());
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
						"Person",
						"Address",
						"OWNED",
						"MANY",
						"FULL",
						"Long",
						true,
						true,
						true,
						true,
						true,
						true,
						true),
				new GeneratedRelationship(
						Property.of(
								"manager",
								"Optional<ManagerAPIModelResponse>",
								"java.util.Optional<de.gupta.clean.crud.generator.example.person.useCases.crud.common.dto.ManagerAPIModelResponse>"),
						"Person",
						"Manager",
						"REFERENCED",
						"ONE",
						"REFERENCE",
						"UUID",
						false,
						false,
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
