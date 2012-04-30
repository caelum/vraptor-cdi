package org.vraptor.interceptor;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import org.vraptor.impl.Route;

/**
 * Intercepts only classes or methods annotated with a certain type.
 * It's a template method.
 * @author sergio
 */
public abstract class AnnotatatedInterceptor implements ControllerInterceptor {

	private @Inject Route route;

	public abstract Class<? extends Annotation> annotation();
	
	@Override
	public final boolean accepts() {
		return route.getControllerClass().isAnnotationPresent(annotation()) || route.getControllerMethod().isAnnotationPresent(annotation());
	}

}
