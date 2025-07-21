package de.gupta.clean.crud.generator.code.generation.model.domain.model;

public record Property(
		String name,
		String capitalizedName,
		ConcreteType baseReturnType,
		boolean isOptional
)
{
}