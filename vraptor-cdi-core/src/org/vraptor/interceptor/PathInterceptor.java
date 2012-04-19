package org.vraptor.interceptor;

import javax.inject.Inject;

import org.vraptor.http.RequestAttribute;

public abstract class PathInterceptor implements ControllerInterceptor {

	@Inject @RequestAttribute
	private String requestUri;
	
	public abstract String path();
	
	@Override
	public final boolean accepts() {
		// TODO support regex, like @Path
		return requestUri.startsWith(path());
	}

	
}
