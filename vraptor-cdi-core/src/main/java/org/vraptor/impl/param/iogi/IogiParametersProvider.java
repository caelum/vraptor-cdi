/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.vraptor.impl.param.iogi;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vraptor.impl.core.ResourceMethod;
import org.vraptor.impl.param.ParameterNameProvider;
import org.vraptor.impl.param.ParametersProvider;
import org.vraptor.validator.Message;

import br.com.caelum.iogi.parameters.Parameter;
import br.com.caelum.iogi.parameters.Parameters;
import br.com.caelum.iogi.reflection.Target;

@RequestScoped
public class IogiParametersProvider implements ParametersProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(IogiParametersProvider.class);
	
	@Inject private ParameterNameProvider nameProvider;
	@Inject private HttpServletRequest servletRequest;
	@Inject private InstantiatorWithErrors instantiator;

	public Object[] getParametersFor(ResourceMethod method, List<Message> errors, ResourceBundle bundle) {
		Parameters parameters = parseParameters(servletRequest);
		List<Target<Object>> targets = createTargets(method);

		List<Object> arguments = instantiateParameters(parameters, targets, errors);

		return arguments.toArray();
	}

	private List<Object> instantiateParameters(Parameters parameters, List<Target<Object>> targets, List<Message> errors) {
		LOGGER.debug("getParametersFor() called with parameters {} and targets {}.", parameters, targets);

		List<Object> arguments = new ArrayList<Object>();
		for (Target<Object> target : targets) {
			Object newObject = instantiateOrAddError(parameters, errors, target);
			arguments.add(newObject);
		}
		return arguments;
	}

	private Object instantiateOrAddError(Parameters parameters, List<Message> errors, Target<Object> target) {
		return instantiator.instantiate(target, parameters, errors);
	}

	private List<Target<Object>> createTargets(ResourceMethod method) {
		Method javaMethod = method.getMethod();
		List<Target<Object>> targets = new ArrayList<Target<Object>>();

		Type[] parameterTypes = javaMethod.getGenericParameterTypes();
		String[] parameterNames = nameProvider.parameterNamesFor(javaMethod);
		for (int i = 0; i < methodArity(javaMethod); i++) {
			if (parameterTypes[i] instanceof TypeVariable) {
				ParameterizedType superclass = (ParameterizedType) method.getResource().getType().getGenericSuperclass();
				parameterTypes[i] = superclass.getActualTypeArguments()[0];
			}
			Target<Object> newTarget = new Target<Object>(parameterTypes[i], parameterNames[i]);
			targets.add(newTarget);
		}

		return targets;
	}

	private int methodArity(Method method) {
		return method.getGenericParameterTypes().length;
	}

	private Parameters parseParameters(HttpServletRequest request) {
		List<Parameter> parameterList = new ArrayList<Parameter>();

		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
			String[] parameterValues = request.getParameterValues(parameterName);
			for (String value : parameterValues) {
				Parameter newParameter = new Parameter(parameterName, value);
				parameterList.add(newParameter);
			}
		}

		return new Parameters(parameterList);
	}

}
