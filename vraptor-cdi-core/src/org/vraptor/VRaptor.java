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

import org.vraptor.impl.core.VRaptorFilterImpl;

@WebFilter(displayName="vraptor", urlPatterns="/*", dispatcherTypes={DispatcherType.FORWARD, DispatcherType.REQUEST})
public class VRaptor implements Filter {

	@Inject private VRaptorFilterImpl vraptor;
	
	private ServletContext servletContext;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		try {
			
			// invoke VRaptor
			vraptor.execute(request, response, filterChain, servletContext);
			
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
		servletContext = config.getServletContext();
	}

	@Produces @ApplicationScoped
	public ServletContext produceServletContext() {
		return servletContext;
	}
	
	@Override
	public void destroy() {
	}
}
