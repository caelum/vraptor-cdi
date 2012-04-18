package org.vraptor.impl;

import java.util.LinkedList;
import java.util.List;

import javax.enterprise.inject.Alternative;

import org.vraptor.ControllerInterceptor;

@Alternative
public class ScannedControllerInterceptors {

	private final List<Class<? extends ControllerInterceptor>> classes = new LinkedList<Class<? extends ControllerInterceptor>>();
	
	public void add(Class<? extends ControllerInterceptor> c) {
		classes.add(c);
	}
	
	public List<Class<? extends ControllerInterceptor>> getClasses() {
		return classes;
	}
}
