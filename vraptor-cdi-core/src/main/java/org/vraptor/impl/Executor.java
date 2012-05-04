package org.vraptor.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.impl.cdi.CDIUtils;
import org.vraptor.interceptor.ControllerInterceptor;
import org.vraptor.interceptor.InterceptorStack;

@ApplicationScoped
public class Executor {

	@Inject
	private ScannedControllerInterceptors interceptors;
	
	@Inject
	private CDIUtils utils;
	
	@Inject	private Instance<Request> request;
	
	public void execute(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, Route route)  {
		this.request.get().init(request, response, servletContext);

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
