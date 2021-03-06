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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.inject.Inject;

import org.vraptor.impl.core.TypeNameExtractor;

public class DefaultParameterNameProvider implements ParameterNameProvider{

	@Inject private TypeNameExtractor extractor;

    public String[] parameterNamesFor(AccessibleObject method) {
        Type[] parameterTypes = parameterTypes(method);
        String[] names = new String[parameterTypes.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = extractor.nameFor(parameterTypes[i]);
        }
        return names;
    }

	@SuppressWarnings({ "rawtypes" })
	private Type[] parameterTypes(AccessibleObject methodOrConstructor) {
		if (methodOrConstructor instanceof Method) {
			return ((Method)methodOrConstructor).getGenericParameterTypes();
		} else if (methodOrConstructor instanceof Constructor<?>) {
			return ((Constructor)methodOrConstructor).getGenericParameterTypes();
		} else {
			throw new IllegalArgumentException("Expecting a method or constructor, " +
					"instead got " + methodOrConstructor);
		}
	}

}
