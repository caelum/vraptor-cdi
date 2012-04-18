package org.vraptor;

import org.vraptor.impl.InterceptorStack;

public interface ControllerInterceptor {
	void intercept(InterceptorStack stack);
	boolean accepts();
}
