package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.configuration;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

	@Test
	void shouldMatchMetadataEntriesToTemplateResources() throws IOException
	{
		try (var stream = Files.walk(Path.of("src/main/resources/templates")))
		{
			var resourceTemplateNames = stream.filter(path -> path.toString().endsWith(".ftl"))
			                                  .map(Path::getFileName)
			                                  .map(Path::toString)
			                                  .map(fileName -> fileName.substring(0, fileName.length() - 4))
			                                  .collect(Collectors.toUnmodifiableSet());

			assertEquals(resourceTemplateNames, TemplateMetadataRegistry.getAllTemplateMetadata().keySet());
		}
	}
}
