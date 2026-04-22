package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

import java.util.SequencedCollection;

public interface TemplateTypeBindings
{
	boolean isGeneric();

	SequencedCollection<String> parameters();

	SequencedCollection<String> apiDomainDifferingParameters();

	SequencedCollection<String> persistenceDomainDifferingParameters();
}
