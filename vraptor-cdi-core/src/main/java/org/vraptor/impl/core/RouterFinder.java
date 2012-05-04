package org.vraptor.impl.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vraptor.impl.Executor;
import org.vraptor.impl.NotFoundException;
import org.vraptor.impl.Route;
import org.vraptor.impl.Router;
import org.vraptor.impl.StaticFunctions;

/**
 * Locates a dynamic or fixed route so we can process the request.
 * 
 * @author Sérgio Lopes
 * @author Guilherme Silveira
 * @author Andre Silva
 */
@ApplicationScoped
public class RouterFinder {

	private final Router router;
	private final Executor executor;
	private final static Logger logger = LoggerFactory.getLogger(RouterFinder.class);

	/**
	 * Weld eyes only.
	 */
	protected RouterFinder() {
		this(null, null);
	}

	@Inject
	public RouterFinder(Router router, Executor executor) {
		super();
		this.router = router;
		this.executor = executor;
	}

	public void execute(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, ServletContext servletContext) throws Exception {
		
		if (fileExists(request, servletContext)) {
			deferToContainer(request, response, filterChain);
			return;
		}

		try {
			deferToVRaptor(request, response, servletContext);
		} catch (NotFoundException e) {
			response.sendError(404);
		}
	}

	private void deferToVRaptor(HttpServletRequest request,
			HttpServletResponse response, ServletContext servletContext) {
		String uri = StaticFunctions.requestUri(request, servletContext);
		Route route = router.routeFor(uri);
		executor.execute(request, response, servletContext, route);
	}

	private void deferToContainer(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		logger.debug("Deferring request to container: {} ", request.getRequestURI());
		filterChain.doFilter(request, response);
	}

	private boolean fileExists(HttpServletRequest request, ServletContext context) throws MalformedURLException {
		URL resourceUrl = context.getResource(uriRelativeToContextRoot(request));
		return resourceUrl != null && isAFile(resourceUrl);
	}

	private String uriRelativeToContextRoot(HttpServletRequest request) {
		return request.getRequestURI().substring(request.getContextPath().length());
	}

	private boolean isAFile(URL resourceUrl) {
		return !resourceUrl.toString().endsWith("/");
	}

}
