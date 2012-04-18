package org.vraptor.impl;

import org.vraptor.ControllerInterceptor;

public class InterceptorStack {

	private final ControllerInterceptor nextInterceptor;
	private final InterceptorStack previousStack;

	InterceptorStack(ControllerInterceptor nextInterceptor, InterceptorStack previousStack) {
		this.nextInterceptor = nextInterceptor;
		this.previousStack = previousStack;
	}
	
	public void proceed() {
		if (nextInterceptor.accepts()) {
			nextInterceptor.intercept(previousStack);
		} else {
			previousStack.proceed();
		}
	}
}
