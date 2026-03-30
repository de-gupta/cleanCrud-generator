<#-- Template for generating JPA converter stubs for complex persistence fields -->
package ${basePackage()}.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
<#if persistenceModelImports()?has_content>
<#list persistenceModelImports() as import>
<#if import != "java.util.Optional" && import != "java.util.UUID">
import ${import};
</#if>
</#list>
</#if>

public final class ${persistenceJpaConvertersTypeName()}
{
<#list jpaConverterProperties() as property>
	@Converter(autoApply = false)
	public static final class ${jpaConverterNestedClassName(property)}
			implements AttributeConverter<${persistenceResolvedType(property.baseType())}, String>
	{
		@Override
		public String convertToDatabaseColumn(final ${persistenceResolvedType(property.baseType())} attribute)
		{
			throw new UnsupportedOperationException(
					"TODO: serialize ${persistenceResolvedType(property.baseType())} for ${modelBaseName()}.${property.name()}");
		}

		@Override
		public ${persistenceResolvedType(property.baseType())} convertToEntityAttribute(final String dbData)
		{
			throw new UnsupportedOperationException(
					"TODO: deserialize ${persistenceResolvedType(property.baseType())} for ${modelBaseName()}.${property.name()}");
		}
	}

</#list>
	private ${persistenceJpaConvertersTypeName()}()
	{
	}
}