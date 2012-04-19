package br.com.caelum.example.interceptor;

import java.lang.annotation.Annotation;

import javax.inject.Inject;

import org.vraptor.Result;
import org.vraptor.http.RequestParam;
import org.vraptor.interceptor.AnnotatatedInterceptor;
import org.vraptor.interceptor.InterceptorStack;

public class AuthInterceptor extends AnnotatatedInterceptor {

	@Inject @RequestParam
	private String admin;
	
	@Inject
	private Result result;

	@Override
	public void intercept(InterceptorStack stack) {
		System.out.println("Checking user permission..");
		
		if (this.admin == null) {
			result.text("Sorry, you must be logged in [add admin param to URL]");
		} else {
			stack.proceed();
		}
	}

	@Override
	public Class<? extends Annotation> annotation() {
		return Admin.class;
	}
}
