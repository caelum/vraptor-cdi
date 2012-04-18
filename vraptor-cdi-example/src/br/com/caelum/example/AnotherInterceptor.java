package br.com.caelum.example;

import org.vraptor.ControllerInterceptor;
import org.vraptor.impl.InterceptorStack;

public class AnotherInterceptor implements ControllerInterceptor {

	@Override
	public void intercept(InterceptorStack stack) {
		System.out.println("[AnotherInterceptor] Dumb one.");
		stack.proceed();
	}

	@Override
	public boolean accepts() {
		return true;
	}
}
