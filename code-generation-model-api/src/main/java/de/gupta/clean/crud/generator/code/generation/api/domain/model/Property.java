package de.gupta.clean.crud.generator.code.generation.api.domain.model;

public record Property(
		String name,
		String capitalizedName,
		ConcreteType baseReturnType,
		boolean isOptional
)
{
}