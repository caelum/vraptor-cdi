package org.vraptor;

import java.io.File;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.impl.Executor;
import org.vraptor.impl.NotFoundException;
import org.vraptor.impl.RequestObjectsProducer;
import org.vraptor.impl.Route;
import org.vraptor.impl.Router;

@ApplicationScoped
@WebFilter(displayName="vraptor", urlPatterns="/*", dispatcherTypes={DispatcherType.FORWARD, DispatcherType.REQUEST})
public class VRaptor implements Filter {

	@Inject 
	private Router router;
	
	@Inject
	private Executor executor;
	
	@Inject
	private Instance<RequestObjectsProducer> requestObjectsProducer;

	private ServletContext servletContext;
		
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

		// get request and response
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		requestObjectsProducer.get().setRequest(request);
		requestObjectsProducer.get().setResponse(response);
		
		// get relative URI
		String uri = request.getRequestURI();
		if (!servletContext.getContextPath().equals("/")) {
			uri = uri.replaceFirst(servletContext.getContextPath(), "");
		}
		
		// if file exists, skips
		if (!uri.equals("/") && new File(servletContext.getRealPath(uri)).exists()) {
			System.out.println("[VRaptor] Delegating to container: " + uri);
			filterChain.doFilter(request, response);
			return;
		}
		
		// VRaptor:
		try {
			
			Route route = router.routeFor(uri);
			requestObjectsProducer.get().setRoute(route);
			
			executor.execute();
			
		} catch (NotFoundException e) {
			response.sendError(404);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		servletContext = config.getServletContext();
	}

	@Produces @ApplicationScoped
	public ServletContext produceServletContext() {
		return servletContext;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
