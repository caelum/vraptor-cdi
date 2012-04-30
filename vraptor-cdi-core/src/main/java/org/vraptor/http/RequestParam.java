package org.vraptor.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {

	// param name - defaults to attribute name
	@Nonbinding String value() default "";
	
	// default value, if null; otherwise, returns null
	String DEFAULT_VALUE_PLACEHOLDER = "org.vraptor.DEFAULT_VALUE_PLACEHOLDER";
	@Nonbinding String defaultValue() default DEFAULT_VALUE_PLACEHOLDER;
}
