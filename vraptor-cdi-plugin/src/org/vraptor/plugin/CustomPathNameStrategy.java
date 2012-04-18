package org.vraptor.plugin;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.vraptor.extensions.PathNameStrategy;
import org.vraptor.impl.Route;

@Decorator
public class CustomPathNameStrategy implements PathNameStrategy {

	private @Inject @Delegate @Any PathNameStrategy pathNameStrategy;
	
	@Override
	public String pathFor(Route route) {
		System.out.println("[CustomPathNameStrategy] Enabled decorator");
		return pathNameStrategy.pathFor(route);
	}

}
