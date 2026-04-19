package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemplateMetadataRegistryTest
{
	@Test
	void shouldExposeNewCrudConfigurationTemplatesAndHideOldStandaloneCrudServiceTemplates()
	{
		assertTrue(TemplateMetadataRegistry.hasTemplate("CrudPortsConfiguration"));
		assertTrue(TemplateMetadataRegistry.hasTemplate("CrudDefinitionConfiguration"));
		assertTrue(TemplateMetadataRegistry.hasTemplate("CrudServicesConfiguration"));
		assertTrue(TemplateMetadataRegistry.hasTemplate("CrudRelationshipConfiguration"));
		assertFalse(TemplateMetadataRegistry.hasTemplate("SaveService"));
		assertFalse(TemplateMetadataRegistry.hasTemplate("FetchService"));
		assertFalse(TemplateMetadataRegistry.hasTemplate("UpdateService"));
		assertFalse(TemplateMetadataRegistry.hasTemplate("DeleteService"));
	}
}