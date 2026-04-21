package de.gupta.clean.crud.generator.api.api.cli;

import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.selection.TemplateGroup;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.SourceCodeTemplate;
import de.gupta.clean.crud.generator.code.generation.template.api.domain.model.template.TemplateMetadata;
import de.gupta.clean.crud.generator.code.generation.template.api.useCases.processing.api.application.TemplateCatalog;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListTemplatesCommandTest
{
	@Test
	void printsTemplateNamesInSortedOrder()
	{
		TemplateCatalog templateCatalog = () -> new LinkedHashSet<>(Set.of(
				new SourceCodeTemplate("ZuluTemplate", "ZuluTemplate.ftl", false,
						TemplateGroup.COMMON, TemplateMetadata.empty()),
				new SourceCodeTemplate("AlphaTemplate", "AlphaTemplate.ftl", false,
						TemplateGroup.COMMON, TemplateMetadata.empty()),
				new SourceCodeTemplate("MikeTemplate", "MikeTemplate.ftl", false,
						TemplateGroup.COMMON, TemplateMetadata.empty())
		));
		var command = new ListTemplatesCommand(templateCatalog);
		var commandLine = new CommandLine(command);
		var output = new ByteArrayOutputStream();
		commandLine.setOut(new PrintWriter(output, true));

		assertEquals(0, commandLine.execute());
		assertEquals("""
				AlphaTemplate
				MikeTemplate
				ZuluTemplate
				""".replace("\n", System.lineSeparator()), output.toString());
	}
}
