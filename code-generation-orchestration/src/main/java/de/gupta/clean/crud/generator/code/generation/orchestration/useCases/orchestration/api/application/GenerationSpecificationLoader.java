package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import de.gupta.clean.crud.template.domain.relationship.Relationships;

import java.nio.file.Path;

public interface GenerationSpecificationLoader
{
	Relationships load(Path baseModelSourceFilePath, Path relationshipsSourceFilePath);
}
