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

import org.vraptor.converter.Converter;

/**
 * Represents a collection of converters.<br>
 * Note: This interface will probably change in the near future to allow
 * annotation support.
 *
 * @author Guilherme Silveira
 * @author Sérgio Lopes
 */
public interface Converters {

	/**
	 * Extracts a converter for this specific type.
	 *
	 * @param type
	 * @param container
	 * @return
	 */
	<T> Converter<T> to(Class<T> type);

	boolean existsFor(Class<?> type);

	//XXX boolean existsTwoWayFor(Class<?> type);

	//XXX TwoWayConverter<?> twoWayConverterFor(Class<?> type);

}
