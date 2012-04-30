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

package org.vraptor.impl.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vraptor.VRaptorException;
import org.vraptor.converter.Converter;
import org.vraptor.impl.converter.PrimitiveConverter;

/**
 * @author Sérgio Lopes
 */
@ApplicationScoped
public class DefaultConverters implements Converters {
	private final Logger logger = LoggerFactory.getLogger(DefaultConverters.class);
	private final Map<Class<?>, Converter<?>> convertersIndex = new HashMap<Class<?>, Converter<?>>();

	@Inject private Instance<Converter<?>> converters;

	@PostConstruct
	void indexConverters() {
		logger.info("Registering converters");
		for (Converter<?> converter: this.converters) {
			logger.debug("  converter to be registered: {}", converter.getClass());
			convertersIndex.put(extractConvertibleType(converter), converter);
		}
	}

    private <T> Class<T> extractConvertibleType(Converter<T> converter) {
    	// TODO change to deep scan
    	// XXX support enums
    	for (Type interfaceType : converter.getClass().getGenericInterfaces()) {
    		if (interfaceType instanceof ParameterizedType) {
    			ParameterizedType type = (ParameterizedType) interfaceType;
        		if (type.getRawType().equals(Converter.class)) {
        			return (Class<T>) type.getActualTypeArguments()[0];
        		} else if (type.getRawType().equals(PrimitiveConverter.class)) {
        			Class<T> wrapper = (Class<T>) type.getActualTypeArguments()[0];
        			try {
        				return (Class<T>) wrapper.getDeclaredField("TYPE").get(null);
        			} catch (Exception e) {
        				throw new VRaptorException(e);
        			}
        		}
    		}
    	}
		
    	throw new VRaptorException("Couldn't find type parameter for " + converter.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public <T> Converter<T> to(Class<T> convertibleType) {
        if (!existsFor(convertibleType)) {
			throw new VRaptorException("Unable to find converter for " + convertibleType.getName());
		}
        return (Converter<T>) convertersIndex.get(convertibleType);
    }

	public boolean existsFor(Class<?> convertibleType) {
		return convertersIndex.containsKey(convertibleType);
	}

//	public boolean existsTwoWayFor(Class<?> type) {
//		Class<? extends Converter<?>> found = findConverterType(type);
//		return found != null && TwoWayConverter.class.isAssignableFrom(found);
//	}
//
//	public TwoWayConverter<?> twoWayConverterFor(Class<?> type) {
//		if (!existsTwoWayFor(type)) {
//			throw new VRaptorException("Unable to find two way converter for " + type.getName());
//		}
//        return (TwoWayConverter<?>) container.instanceFor(findConverterType(type));
//	}

}
