package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

record PostCommitHooksProjectionImpl(
		AggregateDescriptor aggregate,
		boolean saveEnabled,
		boolean updateEnabled,
		boolean deleteEnabled)
		implements PostCommitHooksProjection
{
	@Override
	public boolean anyEnabled()
	{
		return saveEnabled || updateEnabled || deleteEnabled;
	}

	@Override
	public String savePackage()
	{
		return aggregate.basePackage() + ".useCases.crud.postcommit.save";
	}

	@Override
	public String updatePackage()
	{
		return aggregate.basePackage() + ".useCases.crud.postcommit.update";
	}

	@Override
	public String deletePackage()
	{
		return aggregate.basePackage() + ".useCases.crud.postcommit.delete";
	}

	@Override
	public String saveTypeName()
	{
		return aggregate.baseName() + "SavePostCommitMutation";
	}

	@Override
	public String updateTypeName()
	{
		return aggregate.baseName() + "UpdatePostCommitMutation";
	}

	@Override
	public String deleteTypeName()
	{
		return aggregate.baseName() + "DeletePostCommitMutation";
	}

	@Override
	public String saveQualifier()
	{
		return aggregate.qualifier("SavePostCommitMutation");
	}

	@Override
	public String updateQualifier()
	{
		return aggregate.qualifier("UpdatePostCommitMutation");
	}

	@Override
	public String deleteQualifier()
	{
		return aggregate.qualifier("DeletePostCommitMutation");
	}
}
