package org.vraptor.impl;

import java.lang.reflect.Method;

import javax.enterprise.inject.Any;

@Any
public class Route {

	private Class<?> controllerClass;
	private Method controllerMethod;
	
	public Route(Class<?> controllerClass, Method controllerMethod) {
		this.controllerClass = controllerClass;
		this.controllerMethod = controllerMethod;
	}
	Route(){}
	
	public Class<?> getControllerClass() {
		return controllerClass;
	}
	
	public Method getControllerMethod() {
		return controllerMethod;
	}
}
