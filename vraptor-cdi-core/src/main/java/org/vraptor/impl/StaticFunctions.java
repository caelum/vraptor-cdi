package org.vraptor.impl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Many ugly non-OO untestable static procedural functions .
 * @author sergio lopes
 */
public abstract class StaticFunctions {

    public static String requestUri(HttpServletRequest request, ServletContext servletContext) {
        String includeUri = (String) request.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI);
		if (includeUri != null) {
            return includeUri;
        }
		
        String uri = request.getRequestURI().replaceFirst("(?i);jsessionid=.*$", "");
        String contextName = request.getContextPath();
        uri = uri.replaceFirst(contextName, "");
        return uri;
	}
	
}

