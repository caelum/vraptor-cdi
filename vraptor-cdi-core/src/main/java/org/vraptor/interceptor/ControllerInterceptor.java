package org.vraptor.interceptor;


public interface ControllerInterceptor {
	void intercept(InterceptorStack stack);
	boolean accepts();
}
