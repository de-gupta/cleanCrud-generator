package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.freemarker;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.TemplateMetadata;
import de.gupta.clean.crud.generator.code.generation.template.support.TemplateModelTestFixture;
import freemarker.template.Configuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemplateProcessorImplTest
{
	@Test
	void rendersTemplateUsingSemanticTemplateModelAccessors()
	{
		Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		configuration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
		TemplateProcessorImpl processor = new TemplateProcessorImpl(configuration);
		SourceCodeTemplate template = new SourceCodeTemplate(
				"APIModelUpdatePatchDTO",
				"api/APIModelUpdatePatchDTO.ftl",
				false,
				TemplateGroup.API_DTOS,
				TemplateMetadata.empty());

		var rendered = processor.process(template, TemplateModelTestFixture.createPersonTemplateModel());

		assertEquals("PersonAPIModelUpdatePatch.java", rendered.fileName());
		assertTrue(rendered.sourceCode().sourceCode().contains(
				"package de.gupta.clean.crud.generator.example.person.useCases.crud.common.dto;"));
		assertTrue(rendered.sourceCode().sourceCode().contains("public record PersonAPIModelUpdatePatch("));
		assertTrue(rendered.sourceCode().sourceCode().contains("Optional<String> externalId"));
		assertTrue(rendered.sourceCode().sourceCode().contains("Optional<UUID> manager"));
	}
}
