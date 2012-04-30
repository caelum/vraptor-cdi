package org.vraptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;

/**
 * Marks controllers, entry points for requests.
 * @author Sérgio Lopes
 */
@Stereotype
@RequestScoped

@Target({ElementType.TYPE, ElementType.PACKAGE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

	/**
	 * @return Controller name. Defaults to class name.
	 */
	String value() default "";
	
	/**
	 * @return Base path for controller methods. Defaults to lowercase controller name.
	 */
	String path() default "";
}
