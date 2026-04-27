package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.orchestration.configuration.*;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
final class DirectCliConfigurationAssembler
{
	CodeGenerationConfiguration assemble(
			final DirectCliGenerationOptions options,
			final CommandLine.Model.CommandSpec spec)
	{
		var resolvedBaseModel = options.resolvedBaseModelPath();
		if (resolvedBaseModel == null)
		{
			throw new CommandLine.ParameterException(spec.commandLine(),
					"Please provide either `--config <file>` or a base model via positional path / `--base-model`");
		}

		return new CodeGenerationConfiguration(
				new GenerationInputs(
						resolvedBaseModel,
						options.domainModelPath(),
						options.persistenceModelPath(),
						options.apiModelPath(),
						options.relationshipsPath()),
				new LayerConcreteTypes(
						parseAssignments(options.domainTypes(), spec),
						parseAssignments(options.persistenceTypes(), spec),
						parseAssignments(options.apiTypes(), spec)),
				new GenerationSelection(
						options.includeGroups(),
						options.includeTemplates(),
						options.includeTags(),
						options.excludeGroups(),
						options.excludeTemplates(),
						options.excludeTags()),
				List.of(),
				RootAggregateIdConfiguration.defaults(),
				new OwnershipConfiguration(
						options.baseModelOwnership(),
						options.domainModelOwnership(),
						options.persistenceModelOwnership(),
						options.apiModelOwnership(),
						parseOwnershipAssignments(options.ownershipGroupRules(), spec),
						parseOwnershipAssignments(options.ownershipTemplateRules(), spec),
						parseOwnershipAssignments(options.ownershipTagRules(), spec)),
				new OverwriteConfiguration(
						Boolean.TRUE.equals(options.overwriteDefault()),
						parseBooleanAssignments(options.overwriteGroupRules(), spec),
						parseBooleanAssignments(options.overwriteTemplateRules(), spec),
						parseBooleanAssignments(options.overwriteTagRules(), spec),
						parseBooleanAssignments(options.overwriteFileRules(), spec)),
				options.historized());
	}

	Map<String, String> parseAssignments(
			final List<String> assignments,
			final CommandLine.Model.CommandSpec spec)
	{
		if (assignments == null || assignments.isEmpty())
		{
			return Map.of();
		}
		var result = new LinkedHashMap<String, String>();
		assignments.stream()
		           .filter(Objects::nonNull)
		           .map(String::trim)
		           .filter(value -> !value.isBlank())
		           .forEach(assignment ->
				   {
					   var split = assignment.split("=", 2);
					   if (split.length != 2 || split[0].isBlank() || split[1].isBlank())
					   {
						   throw new CommandLine.ParameterException(spec.commandLine(),
								   "Invalid assignment `" + assignment + "`. Expected KEY=VALUE.");
					   }
					   result.put(split[0].trim(), split[1].trim());
				   });
		return Map.copyOf(result);
	}

	Map<String, GeneratedArtifactOwnership> parseOwnershipAssignments(
			final List<String> assignments,
			final CommandLine.Model.CommandSpec spec)
	{
		if (assignments == null || assignments.isEmpty())
		{
			return Map.of();
		}
		var result = new LinkedHashMap<String, GeneratedArtifactOwnership>();
		assignments.stream()
		           .filter(Objects::nonNull)
		           .map(String::trim)
		           .filter(value -> !value.isBlank())
		           .forEach(assignment ->
				   {
					   var split = assignment.split("=", 2);
					   if (split.length != 2 || split[0].isBlank() || split[1].isBlank())
					   {
						   throw new CommandLine.ParameterException(spec.commandLine(),
								   "Invalid ownership rule `" + assignment + "`. Expected KEY=USER|GENERATED.");
					   }
					   result.put(split[0].trim(), GeneratedArtifactOwnership.valueOf(split[1].trim().toUpperCase()));
				   });
		return Map.copyOf(result);
	}

	Map<String, Boolean> parseBooleanAssignments(
			final List<String> assignments,
			final CommandLine.Model.CommandSpec spec)
	{
		if (assignments == null || assignments.isEmpty())
		{
			return Map.of();
		}
		var result = new LinkedHashMap<String, Boolean>();
		assignments.stream()
		           .filter(Objects::nonNull)
		           .map(String::trim)
		           .filter(value -> !value.isBlank())
		           .forEach(assignment ->
				   {
					   var split = assignment.split("=", 2);
					   if (split.length != 2 || split[0].isBlank() || split[1].isBlank())
					   {
						   throw new CommandLine.ParameterException(spec.commandLine(),
								   "Invalid overwrite rule `" + assignment + "`. Expected KEY=true|false.");
					   }
					   result.put(split[0].trim(), Boolean.parseBoolean(split[1].trim()));
				   });
		return Map.copyOf(result);
	}
}
