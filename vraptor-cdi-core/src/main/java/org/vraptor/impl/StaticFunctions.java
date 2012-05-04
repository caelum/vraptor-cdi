package org.vraptor.impl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Many ugly non-OO untestable static procedural functions .
 * @author sergio lopes
 */
public abstract class StaticFunctions {

	public static String requestUri(HttpServletRequest request, ServletContext servletContext) {
		String uri = request.getRequestURI();
		if (!servletContext.getContextPath().equals("/")) {
			uri = uri.replaceFirst(servletContext.getContextPath(), "");
		}
		return uri;
	}
	
}

