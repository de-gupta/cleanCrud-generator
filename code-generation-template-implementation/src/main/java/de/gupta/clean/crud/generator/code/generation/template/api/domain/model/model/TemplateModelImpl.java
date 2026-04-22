package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

record TemplateModelImpl(
		AggregateDescriptor aggregate,
		AggregateComposition composition,
		DomainProjection domain,
		PersistenceProjection persistence,
		ApiProjection api,
		TemplateTypeBindings types)
		implements TemplateModel
{
}