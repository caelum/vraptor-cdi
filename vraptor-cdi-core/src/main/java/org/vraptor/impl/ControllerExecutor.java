package org.vraptor.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vraptor.VRaptorException;
import org.vraptor.impl.cdi.CDIUtils;
import org.vraptor.impl.core.MethodInfo;
import org.vraptor.impl.param.ParametersInstantiator;
import org.vraptor.impl.util.Stringnifier;
import org.vraptor.interceptor.ControllerInterceptor;
import org.vraptor.interceptor.InterceptorStack;
import org.vraptor.validator.ValidationException;
import org.vraptor.validator.Validator;

public class ControllerExecutor implements ControllerInterceptor {
	private final static Logger log = LoggerFactory.getLogger(ControllerExecutor.class);

	@Inject private ParametersInstantiator parametersInstantiator;
	@Inject private MethodInfo info;
	@Inject private Validator validator;
	@Inject private CDIUtils utils;
	
	@Override
	public void intercept(InterceptorStack stack) {
		try {
			// TODO where should we call this?
			parametersInstantiator.execute();
			
			// TODO should this method invocation be in its own class?
			Object resourceObj = utils.getResourceInstanceFromCDI(info.getResourceMethod().getResource().getType());
	
			Method reflectionMethod = info.getResourceMethod().getMethod();
			Object[] parameters = this.info.getParameters();
	
			log.debug("Invoking {}", Stringnifier.simpleNameFor(reflectionMethod));
			Object result = reflectionMethod.invoke(resourceObj, parameters);
	
			// XXX where to go after validation error
	
			if (reflectionMethod.getReturnType().equals(Void.TYPE)) {
				// TODO vraptor2 compatibility??
				this.info.setResult("ok");
			} else {
				this.info.setResult(result);
			}
		} catch (IllegalArgumentException e) {
			throw new VRaptorException(e);
		} catch (IllegalAccessException e) {
			throw new VRaptorException(e);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof ValidationException) {
				// fine... already parsed
				log.trace("swallowing {}", cause);
			} else {
				throw new VRaptorException("exception raised, check root cause for details: " + cause, cause);
			}
		}
	}

	@Override
	public boolean accepts() {
		return true;
	}

}
