package org.vraptor.impl;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.Result;


public class DefaultResult implements Result {

	@Inject
	private HttpServletRequest request;
	
	@Inject
	private HttpServletResponse response;
	
	@Override
	public Result include(String key, Object value) {
		request.setAttribute(key, value);
		return this;
	}

	@Override
	public void sendError(int statusCode) {
		try {
			response.sendError(statusCode);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void forwardTo(String path) {
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void text(String text) {
		try {
			response.getWriter().println(text);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
