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

import org.slf4j.Logger;

public class SimpleWeld implements Filter {
	
	@Inject Xpto xpto;

	@Override
	public void destroy() {
	}

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(VRaptor.class);

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
System.out.println("filtering");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("Starting up Vraptor 4. Ready to fly?");
	}

}
