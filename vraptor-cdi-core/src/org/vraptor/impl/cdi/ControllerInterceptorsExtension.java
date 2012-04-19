package org.vraptor.impl.cdi;

import java.lang.reflect.Modifier;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.vraptor.impl.ScannedControllerInterceptors;
import org.vraptor.interceptor.ControllerInterceptor;

class ControllerInterceptorsExtension implements Extension {

	ScannedControllerInterceptors interceptors = new ScannedControllerInterceptors();
	
	@SuppressWarnings("unchecked")
	public <T> void scanInterceptors(@Observes ProcessAnnotatedType<T> pat, BeanManager beanManager) {
		Class<T> clazz = pat.getAnnotatedType().getJavaClass();
		
		if (clazz != ControllerInterceptor.class && !Modifier.isAbstract(clazz.getModifiers()) && ControllerInterceptor.class.isAssignableFrom(clazz)) {
			System.out.println("Found interceptor " + clazz.getName());
			interceptors.add((Class<? extends ControllerInterceptor>) clazz);
		}
	}
}
