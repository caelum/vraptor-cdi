package org.vraptor.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.vraptor.Path;
import org.vraptor.extension.PathNameStrategy;

@ApplicationScoped
public class Router {

	@Inject
	private ScannedControllers controllers;
	
	@Inject
	private PathNameStrategy nameStrategy;

	private final Map<String, Route> routes = new HashMap<String, Route>();
	
	@PostConstruct
	public void init() {
		for (Class<?> clazz : controllers.getClasses()) {
			register(clazz);
		}
	}
	
	private void register(Class<?> clazz) {
		// find all public methods
		for (Method method : clazz.getDeclaredMethods()) {
			registerRoute(clazz, method);
		}
	}
	
	private void registerRoute(Class<?> clazz, Method method) {
		Route route = new Route(clazz, method);
		
		// discovers the path
		String path;
		if (method.isAnnotationPresent(Path.class)) {
			path = method.getAnnotation(Path.class).value()[0];
		} else {
			// default path
			path = nameStrategy.pathFor(route);
		}
		
		// register route
		System.out.println("[ScannedControllers] New path registered to " + clazz.getSimpleName() + "#" + method.getName() +": " + path);
		routes.put(path, route);
	}
	
	public Route routeFor(String uri) {
		
		for (Entry<String, Route> resource : routes.entrySet()) {
			
			// TODO naive implementation with static paths
			if (resource.getKey().equals(uri)) {
				return resource.getValue();
			}
		}
		
		throw new NotFoundException();
	}	
}
