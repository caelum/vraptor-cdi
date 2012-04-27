package org.vraptor.impl.result;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.impl.core.TypeNameExtractor;
import org.vraptor.result.Result;

public class DefaultResult implements Result {

	@Inject private HttpServletRequest request;
	@Inject private HttpServletResponse response;
	@Inject private TypeNameExtractor extractor;
	
	private final Map<String, Object> includedAttributes = new HashMap<String, Object>();
	private boolean responseCommitted = false;
	
	@Override
	public Result include(String key, Object value) {
		request.setAttribute(key, value);
		includedAttributes.put(key, value);
		return this;
	}

	@Override
	public Map<String, Object> included() {
		return Collections.unmodifiableMap(includedAttributes);
	}

	@Override
	public Result include(Object value) {
		if(value == null) {
			return this;
		}
		
		String key = this.extractor.nameFor(value.getClass());
		return include(key, value);
	}

	@Override
	public void nothing() {
		this.responseCommitted = true;
	}
	
	// XXX implement all methods below this
	
	public void sendError(int statusCode) {
		try {
			response.sendError(statusCode);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

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
	public boolean isCommited() {
		return this.responseCommitted;
	}

	@Override
	public Result on(Class<? extends Exception> exception) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void redirectTo(String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T forwardTo(Class<T> controller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T redirectTo(Class<T> controller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T of(Class<T> controller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T redirectTo(T controller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T forwardTo(T controller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T of(T controller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notFound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void permanentlyRedirectTo(String uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T permanentlyRedirectTo(Class<T> controller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T permanentlyRedirectTo(T controller) {
		// TODO Auto-generated method stub
		return null;
	}
}
