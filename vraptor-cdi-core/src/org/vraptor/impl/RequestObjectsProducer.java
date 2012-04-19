package org.vraptor.impl;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO move to packge-private
@RequestScoped
class RequestObjectsProducer {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Route route;
	
	@Produces @RequestScoped
	public HttpServletRequest producesRequest() {
		return request;
	}
	
	@Produces @RequestScoped
	public HttpServletResponse producesResponse() {
		return response;
	}

	@Produces @RequestScoped
	public Route producesRoute() {
		return route;
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}

	public void init(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext) {
		if (this.request != null) throw new IllegalStateException("You're trying to override request/response.");
		
		this.request = request;
		this.response = response;
	}
}
