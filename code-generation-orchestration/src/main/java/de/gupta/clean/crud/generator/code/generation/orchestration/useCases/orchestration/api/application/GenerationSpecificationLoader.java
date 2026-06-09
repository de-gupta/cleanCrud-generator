package de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

import java.nio.file.Path;

public interface GenerationSpecificationLoader
{
	GenerationSpecificationDescriptor load(Path baseModelSourceFilePath, Path relationshipsSourceFilePath);
}
