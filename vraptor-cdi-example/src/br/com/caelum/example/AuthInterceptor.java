package br.com.caelum.example;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.vraptor.ControllerInterceptor;
import org.vraptor.Result;
import org.vraptor.impl.InterceptorStack;
import org.vraptor.impl.Route;

public class AuthInterceptor implements ControllerInterceptor {

	@Inject
	private HttpServletRequest request;
	
	@Inject
	private Route route;
	
	@Inject
	private Result result;
	
	@Override
	public void intercept(InterceptorStack stack) {
		System.out.println("Checking user permission..");
		
		if (request.getParameter("admin") == null) {
			result.text("Sorry, you must be logged in [add admin param to URL]");
		} else {
			stack.proceed();
		}
	}

	@Override
	public boolean accepts() {
		return route.getControllerClass().isAnnotationPresent(Admin.class);
	}
}
