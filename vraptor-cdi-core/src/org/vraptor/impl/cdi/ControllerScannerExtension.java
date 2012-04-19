package org.vraptor.impl.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.vraptor.Controller;
import org.vraptor.impl.ScannedControllers;

class ControllerScannerExtension implements Extension {

	ScannedControllers controllers = new ScannedControllers();
	
	public <T> void scanControllers(@Observes ProcessAnnotatedType<T> pat, BeanManager beanManager) {
		Class<T> clazz = pat.getAnnotatedType().getJavaClass();
		
		if (Modifier.isAbstract(clazz.getModifiers())) return;
		
		// get all Controller classes. supports two-level qualifiers. 
		// TODO support more levels?
		for (Annotation annotation : clazz.getAnnotations()) {
			if (annotation.annotationType() == Controller.class || annotation.annotationType().isAnnotationPresent(Controller.class)) {
				System.out.println("Found a @Controller " + clazz.getName());
				controllers.add(clazz);
			}
		}
	}
}