package de.gupta.clean.crud.generator.code.generation.template.implementation.useCases.processing.infrastructure.configuration;

import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
class FreemarkerConfiguration
{
	@Bean
	public Configuration freeMarkerConfiguration()
	{
		Configuration freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_31);
		freemarkerConfiguration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
		freemarkerConfiguration.setDefaultEncoding("UTF-8");

		return freemarkerConfiguration;
	}
}