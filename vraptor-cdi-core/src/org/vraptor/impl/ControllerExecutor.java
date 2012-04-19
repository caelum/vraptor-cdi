package org.vraptor.impl;

import java.lang.reflect.InvocationTargetException;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.vraptor.events.BeforeControllerInvocationEvent;
import org.vraptor.impl.cdi.CDIUtils;
import org.vraptor.interceptor.ControllerInterceptor;
import org.vraptor.interceptor.InterceptorStack;

public class ControllerExecutor implements ControllerInterceptor {

	@Inject private Route route;
	@Inject private CDIUtils utils;
	
	@Inject @Any Event<BeforeControllerInvocationEvent> beforeInvocationEvent;
	
	@Override
	public void intercept(InterceptorStack stack) {
		beforeInvocationEvent.fire(new BeforeControllerInvocationEvent(route));
		
		Object resourceObj = utils.getResourceInstanceFromCDI(route.getControllerClass());

		try {
			// XXX simple implementation, without request parameters
			route.getControllerMethod().invoke(resourceObj);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean accepts() {
		return true;
	}

}
