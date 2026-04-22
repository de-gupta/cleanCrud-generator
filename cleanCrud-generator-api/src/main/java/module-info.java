module cleanCrud.generator.api
{
	requires code.generation.orchestration;
	requires code.generation.template.implementation;

	requires info.picocli;
	requires spring.beans;
	requires spring.context;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.dataformat.yaml;

	exports de.gupta.clean.crud.generator.api.api.cli;
	opens de.gupta.clean.crud.generator.api.api.cli to info.picocli;
}
