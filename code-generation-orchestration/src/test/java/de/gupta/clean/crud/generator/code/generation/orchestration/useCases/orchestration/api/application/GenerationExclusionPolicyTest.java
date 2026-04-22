package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.*;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.TemplateMetadata;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.TemplateCatalog;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GenerationExclusionPolicyTest
{
	@Test
	void excludesRelationshipConfigurationWhenRelationshipsAreAbsent()
	{
		var policy = new GenerationExclusionPolicy(() -> Set.of(), new DefaultOwnershipPolicy());
		var configuration = new CodeGenerationConfiguration(
				GenerationInputs.empty(),
				LayerConcreteTypes.defaults(),
				GenerationSelection.defaults(),
				List.of(),
				OwnershipConfiguration.defaults(),
				OverwriteConfiguration.defaults(),
				false
		);

		assertTrue(policy.excludedTemplates(configuration).contains("CrudRelationshipConfiguration"));
	}

	@Test
	void excludesTemplatesOwnedByUser()
	{
		TemplateCatalog templateCatalog = () -> Set.of(
				new SourceCodeTemplate("BaseModel", "BaseModel.ftl", false, TemplateGroup.DOMAIN_MODELS,
						TemplateMetadata.empty()),
				new SourceCodeTemplate("CrudPortsConfiguration", "CrudPortsConfiguration.ftl", false,
						TemplateGroup.CONFIGURATION, TemplateMetadata.empty())
		);
		var policy = new GenerationExclusionPolicy(templateCatalog, new DefaultOwnershipPolicy());
		var configuration = new CodeGenerationConfiguration(
				new GenerationInputs("TaskModel.java", null, null, null),
				LayerConcreteTypes.defaults(),
				GenerationSelection.defaults(),
				List.of(),
				new OwnershipConfiguration(
						de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership.USER,
						de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership.GENERATED,
						de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership.GENERATED,
						de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership.GENERATED,
						Map.of(),
						Map.of(),
						Map.of()
				),
				OverwriteConfiguration.defaults(),
				false
		);

		var excludedTemplates = policy.excludedTemplates(configuration);
		assertTrue(excludedTemplates.contains("BaseModel"));
		assertTrue(excludedTemplates.contains("CrudRelationshipConfiguration"));
	}
}