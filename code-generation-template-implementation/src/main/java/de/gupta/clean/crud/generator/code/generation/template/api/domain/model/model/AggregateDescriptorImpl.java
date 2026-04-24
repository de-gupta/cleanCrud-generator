package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

record AggregateDescriptorImpl(
		String packageName,
		String modelName,
		String rootApiIdType,
		String rootDomainIdType,
		String rootPersistenceIdType,
		boolean historized)
		implements AggregateDescriptor
{
	@Override
	public String basePackage()
	{
		return packageName.endsWith(".domain.model")
				? packageName.substring(0, packageName.length() - ".domain.model".length())
				: packageName;
	}

	@Override
	public String baseName()
	{
		return modelName.endsWith("Model") ? modelName.substring(0, modelName.length() - "Model".length()) :
				modelName;
	}

	@Override
	public String beanNamePrefix()
	{
		return baseName().isEmpty() ? baseName() : Character.toLowerCase(baseName().charAt(0)) + baseName().substring(
				1);
	}

	@Override
	public String qualifier(final String suffix)
	{
		return beanNamePrefix() + suffix;
	}

	@Override
	public String duplicateKeyTypeName()
	{
		return baseName() + "DuplicateKey";
	}

	@Override
	public String rootApiIdType()
	{
		return ProjectionSupport.normalizeGeneratedType(rootApiIdType);
	}

	@Override
	public String rootDomainIdType()
	{
		return ProjectionSupport.normalizeGeneratedType(rootDomainIdType);
	}

	@Override
	public String rootPersistenceIdType()
	{
		return ProjectionSupport.normalizeGeneratedType(rootPersistenceIdType);
	}

	@Override
	public boolean rootApiDomainIdentity()
	{
		return rootApiIdType.equals(rootDomainIdType);
	}

	@Override
	public boolean rootDomainLong()
	{
		return "java.lang.Long".equals(rootDomainIdType) || "Long".equals(rootDomainIdType) || "long".equals(
				rootDomainIdType);
	}
}