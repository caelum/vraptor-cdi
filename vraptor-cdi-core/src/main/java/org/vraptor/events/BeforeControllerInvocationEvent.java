package org.vraptor.events;

import org.vraptor.impl.Route;

/**
 * Triggers just before the controller method is invoked
 * @author sergio
 */
public class BeforeControllerInvocationEvent {

	private final Route route;
	public BeforeControllerInvocationEvent(Route route) {
		this.route = route;
	}
	
	public Route getRoute() {
		return route;
	}
}
