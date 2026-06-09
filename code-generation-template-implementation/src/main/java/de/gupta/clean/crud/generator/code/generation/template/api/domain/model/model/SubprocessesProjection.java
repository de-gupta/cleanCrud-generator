package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

public interface SubprocessesProjection
{
	boolean saveEnabled();

	boolean updateEnabled();

	boolean deleteEnabled();

	boolean anyEnabled();

	String savePackage();

	String updatePackage();

	String deletePackage();

	String saveTriggerTypeName();

	String updateTriggerTypeName();

	String deleteTriggerTypeName();

	String savePayloadTypeName();

	String updatePayloadTypeName();

	String deletePayloadTypeName();

	String saveExecutorTypeName();

	String updateExecutorTypeName();

	String deleteExecutorTypeName();

	String saveConfigurationTypeName();

	String updateConfigurationTypeName();

	String deleteConfigurationTypeName();

	String saveDefinitionQualifier();

	String updateDefinitionQualifier();

	String deleteDefinitionQualifier();

	String saveProcessType();

	String updateProcessType();

	String deleteProcessType();
}
