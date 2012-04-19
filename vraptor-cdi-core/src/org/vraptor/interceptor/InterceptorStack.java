package org.vraptor.interceptor;


public class InterceptorStack {

	private final ControllerInterceptor nextInterceptor;
	private final InterceptorStack previousStack;

	public InterceptorStack(ControllerInterceptor nextInterceptor, InterceptorStack previousStack) {
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
