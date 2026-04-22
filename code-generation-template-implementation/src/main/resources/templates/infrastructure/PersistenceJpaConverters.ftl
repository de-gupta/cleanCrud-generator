<#-- Template for generating JPA converter stubs for complex persistence fields -->
package ${aggregate().basePackage()}.infrastructure.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
<#if persistence().imports()?has_content>
<#list persistence().imports() as import>
<#if import != "java.util.Optional" && import != "java.util.UUID">
import ${import};
</#if>
</#list>
</#if>

public final class ${persistence().jpaConvertersTypeName()}
{
<#list persistence().converterProperties() as property>
	@Converter(autoApply = false)
	public static final class ${persistence().converterNestedClassName(property)}
			implements AttributeConverter<${persistence().resolvedType(property.baseType())}, String>
	{
		@Override
		public String convertToDatabaseColumn(final ${persistence().resolvedType(property.baseType())} attribute)
		{
			throw new UnsupportedOperationException(
					"TODO: serialize ${persistence().resolvedType(property.baseType())} for ${aggregate().baseName()}.${property.name()}");
		}

		@Override
		public ${persistence().resolvedType(property.baseType())} convertToEntityAttribute(final String dbData)
		{
			throw new UnsupportedOperationException(
					"TODO: deserialize ${persistence().resolvedType(property.baseType())} for ${aggregate().baseName()}.${property.name()}");
		}
	}

</#list>
	private ${persistence().jpaConvertersTypeName()}()
	{
	}
}