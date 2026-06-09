package de.gupta.clean.crud.generator.code.generation.template.api.domain.model.model;

public interface PostCommitHooksProjection
{
	boolean saveEnabled();

	boolean updateEnabled();

	boolean deleteEnabled();

	boolean anyEnabled();

	String savePackage();

	String updatePackage();

	String deletePackage();

	String saveTypeName();

	String updateTypeName();

	String deleteTypeName();

	String saveQualifier();

	String updateQualifier();

	String deleteQualifier();
}
