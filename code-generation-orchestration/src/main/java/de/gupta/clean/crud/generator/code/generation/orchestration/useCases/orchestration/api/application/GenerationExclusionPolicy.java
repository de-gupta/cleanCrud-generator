package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.GeneratedArtifactOwnership;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.OwnershipResolver;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.TemplateCatalog;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
final class GenerationExclusionPolicy
{
	private static final String RELATIONSHIP_CONFIGURATION_TEMPLATE = "CrudRelationshipConfiguration";
	private static final java.util.Set<String> SAVE_POST_COMMIT_TEMPLATES = java.util.Set.of(
			"SavePostCommitMutation");
	private static final java.util.Set<String> UPDATE_POST_COMMIT_TEMPLATES = java.util.Set.of(
			"UpdatePostCommitMutation");
	private static final java.util.Set<String> DELETE_POST_COMMIT_TEMPLATES = java.util.Set.of(
			"DeletePostCommitMutation");
	private static final java.util.Set<String> SAVE_SUBPROCESS_TEMPLATES = java.util.Set.of(
			"SaveSubprocessTrigger",
			"SaveSubprocessPayload",
			"SaveSubprocessExecutor",
			"SaveSubprocessConfiguration");
	private static final java.util.Set<String> UPDATE_SUBPROCESS_TEMPLATES = java.util.Set.of(
			"UpdateSubprocessTrigger",
			"UpdateSubprocessPayload",
			"UpdateSubprocessExecutor",
			"UpdateSubprocessConfiguration");
	private static final java.util.Set<String> DELETE_SUBPROCESS_TEMPLATES = java.util.Set.of(
			"DeleteSubprocessTrigger",
			"DeleteSubprocessPayload",
			"DeleteSubprocessExecutor",
			"DeleteSubprocessConfiguration");

	private final TemplateCatalog templateCatalog;
	private final DefaultOwnershipPolicy defaultOwnershipPolicy;

	Set<String> excludedTemplates(final CodeGenerationConfiguration configuration)
	{
		var templates = new LinkedHashSet<>(configuration.generation().excludeTemplates());
		applyRelationshipGenerationExclusions(templates, configuration);
		applyExtensionGenerationExclusions(templates, configuration);
		applyOwnershipExclusions(templates, configuration);
		return Set.copyOf(templates);
	}

	private void applyRelationshipGenerationExclusions(
			final Set<String> templates,
			final CodeGenerationConfiguration configuration)
	{
		if (configuration.relationships().isEmpty())
		{
			templates.add(RELATIONSHIP_CONFIGURATION_TEMPLATE);
		}
	}

	private void applyExtensionGenerationExclusions(
			final Set<String> templates,
			final CodeGenerationConfiguration configuration)
	{
		if (!configuration.postCommitHooks().save())
		{
			templates.addAll(SAVE_POST_COMMIT_TEMPLATES);
		}
		if (!configuration.postCommitHooks().update())
		{
			templates.addAll(UPDATE_POST_COMMIT_TEMPLATES);
		}
		if (!configuration.postCommitHooks().delete())
		{
			templates.addAll(DELETE_POST_COMMIT_TEMPLATES);
		}
		if (!configuration.subprocesses().save())
		{
			templates.addAll(SAVE_SUBPROCESS_TEMPLATES);
		}
		if (!configuration.subprocesses().update())
		{
			templates.addAll(UPDATE_SUBPROCESS_TEMPLATES);
		}
		if (!configuration.subprocesses().delete())
		{
			templates.addAll(DELETE_SUBPROCESS_TEMPLATES);
		}
	}

	private void applyOwnershipExclusions(
			final Set<String> templates,
			final CodeGenerationConfiguration configuration)
	{
		var ownershipResolver = OwnershipResolver.with(configuration.ownership());
		templateCatalog.allTemplates().forEach(template ->
		{
			var ownership = ownershipResolver.ownershipOf(template)
			                                 .orElseGet(() -> defaultOwnershipPolicy.ownershipOf(
													 template.templateName(), configuration));
			if (ownership == GeneratedArtifactOwnership.USER)
			{
				templates.add(template.templateName());
			}
		});
	}

	GenerationExclusionPolicy(
			final TemplateCatalog templateCatalog,
			final DefaultOwnershipPolicy defaultOwnershipPolicy)
	{
		this.templateCatalog = templateCatalog;
		this.defaultOwnershipPolicy = defaultOwnershipPolicy;
	}
}
