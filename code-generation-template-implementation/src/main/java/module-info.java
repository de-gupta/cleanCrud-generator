module code.generation.template.implementation
{
	exports de.gupta.clean.crud.generator.code.generation.template.api.domain.model.exceptions;
	exports de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application;
	exports de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template;
	exports de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;
	exports de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection;

	requires code.generation.model.implementation;
	requires freemarker;
	requires spring.beans;
	requires spring.context;
	requires de.gupta.aletheia;
	requires spring.core;
}
