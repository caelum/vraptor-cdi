package org.vraptor.http;

import java.lang.annotation.Annotation;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.impl.StaticFunctions;

@RequestScoped
class HttpValuesProducer {
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private HttpServletResponse response;
	
	@Inject
	private ServletContext servletContext;

	@Produces @RequestAttribute
	public String produceRequestAttribute(InjectionPoint ip) {
		String key = ip.getMember().getName();
		
		if ("requestUri".equals(key)) {
			return StaticFunctions.requestUri(request, servletContext); // TODO cache this? can't use @RequestScoped here
		}
		
		// TODO exposes more values here?
		
		throw new IllegalArgumentException("Can't find request attribute named " + key);
	}
	
	@Produces @RequestParam
	public String produceParameter(InjectionPoint ip) {
		String key = ip.getMember().getName();
		String defaultValue = null;
		
		for (Annotation a : ip.getQualifiers()) {
			if (a.annotationType() == RequestParam.class) {
				RequestParam rp = (RequestParam) a;
				key = !rp.value().equals("")? rp.value() : key;
				defaultValue = !rp.defaultValue().equals(RequestParam.DEFAULT_VALUE_PLACEHOLDER) ? rp.defaultValue() : defaultValue;
				break;
			}
		}
		
		String value = request.getParameter(key);
		if (value == null) value = defaultValue;
		return value;
	}
	
	// TODO create a @RequestHeader annotation
}
