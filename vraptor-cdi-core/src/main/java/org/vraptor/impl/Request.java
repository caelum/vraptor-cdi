package org.vraptor.impl;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.impl.core.DefaultResourceClass;
import org.vraptor.impl.core.DefaultResourceMethod;
import org.vraptor.impl.core.ResourceMethod;
import org.vraptor.impl.http.MutableRequest;
import org.vraptor.impl.http.MutableResponse;
import org.vraptor.impl.http.VRaptorRequest;
import org.vraptor.impl.http.VRaptorResponse;

@RequestScoped
class Request {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Route route;
	private ResourceMethod resourceMethod;

	@Produces @RequestScoped
	public MutableRequest producesRequest() {
		return new VRaptorRequest(request);
	}
	
	@Produces @RequestScoped
	public MutableResponse producesResponse() {
		return new VRaptorResponse(response);
	}

	@Produces @RequestScoped
	public Route producesRoute() {
		return route;
	}
	
	@Produces @RequestScoped
	public ResourceMethod producesControllerMethod() {
		return this.resourceMethod;
	}
	
	public void setRoute(Route route) {
		this.route = route;
		
		// translate to vraptor3 object
		// TODO remove interface from ResourceMethod/Class?
		this.resourceMethod = new DefaultResourceMethod(new DefaultResourceClass(route.getControllerClass()), route.getControllerMethod());
	}

	public void init(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
		if (this.request != null) throw new IllegalStateException("You're trying to override request/response.");
		
		this.request = request;
		this.response = response;
	}
}
