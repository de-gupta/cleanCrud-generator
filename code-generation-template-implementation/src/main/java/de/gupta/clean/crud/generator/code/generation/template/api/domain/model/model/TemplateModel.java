package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

public interface TemplateModel
{
	AggregateDescriptor aggregate();

	AggregateComposition composition();

	DomainProjection domain();

	PersistenceProjection persistence();

	ApiProjection api();

	TemplateTypeBindings types();
}
