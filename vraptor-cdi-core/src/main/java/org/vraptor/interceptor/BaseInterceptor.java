package org.vraptor.interceptor;

public abstract class BaseInterceptor implements ControllerInterceptor {

	@Override
	public boolean accepts() {
		return true;
	}

}
