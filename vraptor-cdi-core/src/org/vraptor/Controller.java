package org.vraptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Stereotype;

@Stereotype
@RequestScoped

@Target({ElementType.TYPE, ElementType.PACKAGE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

}
