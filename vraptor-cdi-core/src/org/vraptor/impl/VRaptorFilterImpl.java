package org.vraptor.impl;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class VRaptorFilterImpl {

	@Inject	private Router router;
	@Inject	private Executor executor;
	
	@Inject	private Instance<RequestObjectsProducer> requestObjectsProducer;

	public void execute(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, ServletContext servletContext) throws Exception {
		// if file exists, skips
		String uri = StaticFunctions.requestUri(request, servletContext);
		if (!uri.equals("/") && new File(servletContext.getRealPath(uri)).exists()) {
			System.out.println("[VRaptor] Delegating to container: " + uri);
			filterChain.doFilter(request, response);
			return;
		}

		// outject request values do CDI
		requestObjectsProducer.get().init(request, response, servletContext);

		// VRaptor:
		try {
			Route route = router.routeFor(uri);
			requestObjectsProducer.get().setRoute(route);
			executor.execute();
			
		} catch (NotFoundException e) {
			response.sendError(404);
		}
	}
}
