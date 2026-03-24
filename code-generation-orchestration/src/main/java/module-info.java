module code.generation.orchestration
{
	exports de.gupta.clean.crud.generator.code.generation.orchestration.configuration;
	exports de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;

	requires code.generation.model.implementation;
	requires code.generation.template.implementation;
	requires code.generation.writing.implementation;
	requires spring.context;
}