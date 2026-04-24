package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

public interface AggregateDescriptor
{
	String packageName();

	String basePackage();

	String modelName();

	String baseName();

	String beanNamePrefix();

	String qualifier(String suffix);

	String duplicateKeyTypeName();

	String rootApiIdType();

	String rootDomainIdType();

	String rootPersistenceIdType();

	boolean rootApiDomainIdentity();

	boolean rootDomainLong();

	boolean historized();
}
