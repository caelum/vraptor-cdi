package org.vraptor.impl.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vraptor.Controller;
import org.vraptor.impl.ScannedControllers;

class ControllerScannerExtension implements Extension {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerScannerExtension.class);

	ScannedControllers controllers = new ScannedControllers();
	
	public <T> void scanControllers(@Observes ProcessAnnotatedType<T> pat, BeanManager beanManager) {
		Class<T> clazz = pat.getAnnotatedType().getJavaClass();
		
		if (Modifier.isAbstract(clazz.getModifiers())) return;
		
		if(isController(clazz)) {
			logger.debug("Found a @Controller " + clazz.getName());
			controllers.add(clazz);
		}
	}

	private <T> boolean isController(Class<T> clazz) {
		if(isControllerPackage(clazz)) {
			return true;
		}

		for (Annotation annotation : clazz.getAnnotations()) {
			if (isControllerAnnotated(annotation)) {
				return true;
			}
		}
		return false;
	}

	private boolean isControllerAnnotated(Annotation annotation) {
		return annotation.annotationType() == Controller.class || annotation.annotationType().isAnnotationPresent(Controller.class);
	}

	private <T> boolean isControllerPackage(Class<T> clazz) {
		return clazz.getPackage().getName().contains(".controller.") || clazz.getPackage().getName().endsWith(".controller");
	}
}