<#assign parentPackage = basePackage()?keep_before_last(".")>
package ${parentPackage}.configuration.web.advice;

import de.gupta.clean.crud.template.infrastructure.web.AbstractSpringRestControllerAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
class GlobalSpringRestControllerAdvice extends AbstractSpringRestControllerAdvice
{
}