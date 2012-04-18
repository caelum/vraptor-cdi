package org.vraptor.impl;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO move to packge-private
@RequestScoped
public class RequestObjectsProducer {

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
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void setRoute(Route route) {
		this.route = route;
	}
}
