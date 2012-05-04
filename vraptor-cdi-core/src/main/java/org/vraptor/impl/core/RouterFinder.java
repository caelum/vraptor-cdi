package org.vraptor.impl.core;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
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
		String uri = StaticFunctions.requestUri(request, servletContext);
		
		if (fileExists(servletContext, uri)) {
			logger.debug("[VRaptor] Delegating request container: " + uri);
			filterChain.doFilter(request, response);
			return;
		}

		try {
			Route route = router.routeFor(uri);
			executor.execute(request, response, servletContext, route);
		} catch (NotFoundException e) {
			response.sendError(404);
		}
	}

	private boolean fileExists(ServletContext servletContext, String uri) {
		return !uri.equals("/") && new File(servletContext.getRealPath(uri)).exists();
	}
}
