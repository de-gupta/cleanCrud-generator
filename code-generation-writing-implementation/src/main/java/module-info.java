module code.generation.writing.implementation
{
	exports de.gupta.clean.crud.generator.code.generation.writing.api.domain.model;
	exports de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.api.application;
	exports de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.facade;
	exports de.gupta.clean.crud.generator.code.generation.writing.api.useCases.processing.application.service;

	requires spring.context;
	requires de.gupta.athena;
}