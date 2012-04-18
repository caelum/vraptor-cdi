package org.vraptor.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.vraptor.ControllerInterceptor;
import org.vraptor.cdi.CDIUtils;

@ApplicationScoped
public class Executor {

	@Inject
	private ScannedControllerInterceptors interceptors;
	
	@Inject
	private CDIUtils utils;
	
	public void execute()  {
		ControllerInterceptor firstInterceptor = utils.getResourceInstanceFromCDI(ControllerExecutor.class);
		InterceptorStack firstStack = new InterceptorStack(firstInterceptor, null);
		InterceptorStack lastStack = firstStack;
		
		for (Class<? extends ControllerInterceptor> interceptorClass : interceptors.getClasses()) {
			if (interceptorClass == ControllerExecutor.class) continue;
			
			ControllerInterceptor interceptor = utils.getResourceInstanceFromCDI(interceptorClass);
			lastStack = new InterceptorStack(interceptor, lastStack);
		}
		
		lastStack.proceed();
	}
}
