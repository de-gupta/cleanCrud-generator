package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

record SubprocessesProjectionImpl(
		AggregateDescriptor aggregate,
		boolean saveEnabled,
		boolean updateEnabled,
		boolean deleteEnabled)
		implements SubprocessesProjection
{
	@Override
	public boolean anyEnabled()
	{
		return saveEnabled || updateEnabled || deleteEnabled;
	}

	@Override
	public String savePackage()
	{
		return aggregate.basePackage() + ".useCases.process.crud.save";
	}

	@Override
	public String updatePackage()
	{
		return aggregate.basePackage() + ".useCases.process.crud.update";
	}

	@Override
	public String deletePackage()
	{
		return aggregate.basePackage() + ".useCases.process.crud.delete";
	}

	@Override
	public String saveTriggerTypeName()
	{
		return aggregate.baseName() + "SaveSubprocessTrigger";
	}

	@Override
	public String updateTriggerTypeName()
	{
		return aggregate.baseName() + "UpdateSubprocessTrigger";
	}

	@Override
	public String deleteTriggerTypeName()
	{
		return aggregate.baseName() + "DeleteSubprocessTrigger";
	}

	@Override
	public String savePayloadTypeName()
	{
		return aggregate.baseName() + "SaveSubprocessPayload";
	}

	@Override
	public String updatePayloadTypeName()
	{
		return aggregate.baseName() + "UpdateSubprocessPayload";
	}

	@Override
	public String deletePayloadTypeName()
	{
		return aggregate.baseName() + "DeleteSubprocessPayload";
	}

	@Override
	public String saveExecutorTypeName()
	{
		return aggregate.baseName() + "SaveSubprocessExecutor";
	}

	@Override
	public String updateExecutorTypeName()
	{
		return aggregate.baseName() + "UpdateSubprocessExecutor";
	}

	@Override
	public String deleteExecutorTypeName()
	{
		return aggregate.baseName() + "DeleteSubprocessExecutor";
	}

	@Override
	public String saveConfigurationTypeName()
	{
		return aggregate.baseName() + "SaveSubprocessConfiguration";
	}

	@Override
	public String updateConfigurationTypeName()
	{
		return aggregate.baseName() + "UpdateSubprocessConfiguration";
	}

	@Override
	public String deleteConfigurationTypeName()
	{
		return aggregate.baseName() + "DeleteSubprocessConfiguration";
	}

	@Override
	public String saveDefinitionQualifier()
	{
		return aggregate.qualifier("SaveSubprocessDefinition");
	}

	@Override
	public String updateDefinitionQualifier()
	{
		return aggregate.qualifier("UpdateSubprocessDefinition");
	}

	@Override
	public String deleteDefinitionQualifier()
	{
		return aggregate.qualifier("DeleteSubprocessDefinition");
	}

	@Override
	public String saveProcessType()
	{
		return "subprocess." + aggregate.beanNamePrefix() + ".save";
	}

	@Override
	public String updateProcessType()
	{
		return "subprocess." + aggregate.beanNamePrefix() + ".update";
	}

	@Override
	public String deleteProcessType()
	{
		return "subprocess." + aggregate.beanNamePrefix() + ".delete";
	}
}
