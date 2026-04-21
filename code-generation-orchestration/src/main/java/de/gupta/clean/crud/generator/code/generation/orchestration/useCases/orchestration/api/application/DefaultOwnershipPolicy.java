package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
final class DefaultOwnershipPolicy
{
	private static final Set<String> BASE_MODEL_TEMPLATES = Set.of("BaseModel");
	private static final Set<String> DOMAIN_MODEL_TEMPLATES = Set.of(
			"DomainModel",
			"DomainModelImpl",
			"DomainModelBuilder",
			"DomainModelBuilderFactory",
			"DomainModelCreateDTO",
			"DomainModelUpdatePatchDTO",
			"DomainModelResponseDTO",
			"DomainModelPatcher",
			"DomainResponseBuilder");
	private static final Set<String> PERSISTENCE_MODEL_TEMPLATES = Set.of(
			"PersistenceModel",
			"PersistenceModelImpl",
			"PersistenceModelBuilderFactory",
			"PersistenceJpaConverters");
	private static final Set<String> API_MODEL_TEMPLATES = Set.of(
			"APIModelCreateDTO",
			"APIModelResponseDTO",
			"APIModelUpdatePatchDTO");

	GeneratedArtifactOwnership ownershipOf(
			final String templateName,
			final CodeGenerationConfiguration configuration)
	{
		if (BASE_MODEL_TEMPLATES.contains(templateName))
		{
			return configuration.ownership().baseModel();
		}
		if (DOMAIN_MODEL_TEMPLATES.contains(templateName))
		{
			return configuration.ownership().domainModel();
		}
		if (PERSISTENCE_MODEL_TEMPLATES.contains(templateName))
		{
			return configuration.ownership().persistenceModel();
		}
		if (API_MODEL_TEMPLATES.contains(templateName))
		{
			return configuration.ownership().apiModel();
		}
		return GeneratedArtifactOwnership.GENERATED;
	}
}
