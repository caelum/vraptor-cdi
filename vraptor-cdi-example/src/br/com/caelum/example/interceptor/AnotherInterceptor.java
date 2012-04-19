package br.com.caelum.example.interceptor;

import org.vraptor.interceptor.ControllerInterceptor;
import org.vraptor.interceptor.InterceptorStack;

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
