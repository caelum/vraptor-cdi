package br.com.caelum.example.observer;

import javax.enterprise.event.Observes;

import org.vraptor.events.BeforeControllerInvocationEvent;
import org.vraptor.impl.Route;

public class AppObservers {

	public void beforeController(@Observes BeforeControllerInvocationEvent event) {
		Route route = event.getRoute();
		System.out.println("[Just Before Action] Calling " + route.getControllerClass().getSimpleName() + "#" + route.getControllerMethod().getName());
	}
}
