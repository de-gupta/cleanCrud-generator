module code.generation.orchestration
{
	exports de.gupta.clean.crud.generator.code.generation.orchestration.useCases.orchestration.api.application;
	requires code.generation.model.api;
	requires code.generation.template.api;
	requires code.generation.writing.api;

	requires spring.context;
}