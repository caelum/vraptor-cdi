/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.vraptor.impl.param;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vraptor.http.HeaderParam;
import org.vraptor.impl.core.Localization;
import org.vraptor.impl.core.MethodInfo;
import org.vraptor.impl.core.ResourceMethod;
import org.vraptor.impl.http.MutableRequest;
import org.vraptor.validator.Message;
import org.vraptor.validator.Validator;

/**
 * Find parameters to controller method invocation
 *
 * @author Guilherme Silveira
 * @author Sérgio Lopes
 */
@RequestScoped
public class ParametersInstantiator {
	private static final Logger logger = LoggerFactory.getLogger(ParametersInstantiator.class);

	@Inject private ParametersProvider provider;
	@Inject private MethodInfo info;
	@Inject private MutableRequest request;
	@Inject private Localization localization;

	private final List<Message> errors = new ArrayList<Message>();

	@Inject private Validator validator;

	// XXX support flash scope
	//	@Inject private FlashScope flash;

	public void execute() {
		if (hasParameters()) {
			return;
		}
		
    	Enumeration<String> names = request.getParameterNames();
    	while (names.hasMoreElements()) {
			fixParameter(names.nextElement());
		}
    	
    	addHeaderParametersToAttribute(info.getResourceMethod());
    	
        Object[] values = getParametersFor(info.getResourceMethod());

        validator.addAll(errors);

    	if (!errors.isEmpty()) {
    		logger.debug("There are conversion errors: {}", errors);
    	}
        logger.debug("Parameter values for {} are {}", info.getResourceMethod(), values);

        info.setParameters(values);
    }

	private boolean hasParameters() {
		return info.getResourceMethod().getMethod().getParameterTypes().length == 0;
	}

	private void addHeaderParametersToAttribute(ResourceMethod method) {
		Method trueMethod = method.getMethod();
    	Annotation[][] parameterAnnotations = trueMethod.getParameterAnnotations();

		for (Annotation[] annotations : parameterAnnotations) {
			for (Annotation annotation : annotations) {
				if(annotation instanceof HeaderParam){
			        HeaderParam headerParam = (HeaderParam) annotation;
			        String value = request.getHeader(headerParam.value());
			        request.setAttribute(headerParam.value(), value);
			    }
			}
		}
	}

	private void fixParameter(String name) {
		if (name.contains(".class.")) {
			throw new IllegalArgumentException("Bug Exploit Attempt with parameter: " + name + "!!!");
		}
		if (name.contains("[]")) {
			String[] values = request.getParameterValues(name);
			for (int i = 0; i < values.length; i++) {
				request.setParameter(name.replace("[]", "[" + i + "]"), values[i]);
			}
		}
	}

	private Object[] getParametersFor(ResourceMethod method) {
		// XXX implement flash scope
		// Object[] args = flash.consumeParameters(method);
		// if (args == null) {
			return provider.getParametersFor(method, errors, localization.getBundle());
		// }
		// return args;
	}
}
