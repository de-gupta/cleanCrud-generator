package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.generator.code.generation.model.api.domain.model.Model;
import de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application.DomainModelParser;
import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.CodeGenerationConfiguration;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
final class GenerationContextResolver
{
	private final DomainModelParser modelParser;
	private final GenerationSpecificationLoader generationSpecificationLoader;
	private final GenerationSpecificationConfigurationAssembler generationSpecificationConfigurationAssembler;

	ResolvedGenerationRequest resolve(final CodeGenerationConfiguration configuration)
	{
		Model model = modelParser.parseDomainModel(configuration.inputs().baseModelSourceCodeFilePath());
		return new ResolvedGenerationRequest(model, effectiveConfiguration(configuration, model));
	}

	private CodeGenerationConfiguration effectiveConfiguration(
			final CodeGenerationConfiguration configuration,
			final Model model)
	{
		String specSourcePath = configuration.inputs().relationshipsSourceCodeFilePath();
		if (specSourcePath == null || specSourcePath.isBlank())
		{
			return configuration;
		}
		var specification = generationSpecificationLoader.load(
				Path.of(configuration.inputs().baseModelSourceCodeFilePath()),
				Path.of(specSourcePath));
		var loaded = generationSpecificationConfigurationAssembler.assemble(model, specification,
				configuration.rootAggregateIds());
		return new CodeGenerationConfiguration(
				configuration.inputs(),
				configuration.genericTypes(),
				configuration.generation(),
				loaded.relationships(),
				loaded.rootAggregateIds(),
				configuration.ownership(),
				configuration.overwrite(),
				configuration.postCommitHooks().or(loaded.postCommitHooks()),
				configuration.subprocesses().or(loaded.subprocesses()),
				configuration.historized());
	}

	GenerationContextResolver(
			final DomainModelParser modelParser,
			final GenerationSpecificationLoader generationSpecificationLoader,
			final GenerationSpecificationConfigurationAssembler generationSpecificationConfigurationAssembler)
	{
		this.modelParser = modelParser;
		this.generationSpecificationLoader = generationSpecificationLoader;
		this.generationSpecificationConfigurationAssembler = generationSpecificationConfigurationAssembler;
	}
}
