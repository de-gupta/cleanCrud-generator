module code.generation.model.implementation
{
	exports de.gupta.clean.crud.generator.code.generation.model.api.domain.model;
	exports de.gupta.clean.crud.generator.code.generation.model.api.domain.model.exceptions;
	exports de.gupta.clean.crud.generator.code.generation.model.api.useCases.parsing.api.application;

	requires de.gupta.athena;
	requires spring.context;
	requires com.github.javaparser.core;
	requires aletheia;
	requires com.github.javaparser.symbolsolver.core;
}