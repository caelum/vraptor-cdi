package org.vraptor;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
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

import org.slf4j.Logger;
import org.vraptor.impl.core.RouterFinder;

/**
 * Basic vraptor entry point. Processes all requests.
 * 
 * @author guilherme silveira
 * @author sergio lopes
 */
@WebFilter(displayName = "vraptor4", urlPatterns = "/*", dispatcherTypes = {
		DispatcherType.FORWARD, DispatcherType.REQUEST })
public class VRaptor implements Filter {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(VRaptor.class);

	@Inject private RouterFinder vraptor;
	
	private ServletContext context;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		logger.trace("Request: {} / Parameters: {}", req, req.getParameterMap());

		try {
			vraptor.execute(request, response, filterChain, context);
		} catch (ServletException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("Starting up Vraptor 4. Ready to fly?");
		context = config.getServletContext();
	}

	@Produces @ApplicationScoped
	public ServletContext produceServletContext() {
		return context;
	}
	
	@Override
	public void destroy() {
		context = null;
	}
}
