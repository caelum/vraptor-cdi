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

package org.vraptor.impl.converter.jodatime;

import java.text.MessageFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.vraptor.converter.ConversionError;
import org.vraptor.converter.Converter;
import org.vraptor.impl.core.Localization;

/**
 * VRaptor converter for {@link LocalDate}. {@link LocalDate} is part of Joda Time library.
 * 
 * @author Lucas Cavalcanti
 */
public class LocalDateConverter implements Converter<LocalDate> {

	@Inject private Instance<Localization> localization;

    public LocalDate convert(String value, Class<? extends LocalDate> type, ResourceBundle bundle) {
		try {
			Date date = new LocaleBasedJodaTimeConverter(localization.get()).convert(value, type);
			if (date == null) {
				return null;
			}
			return LocalDate.fromDateFields(date);
		} catch (Exception e) {
			throw new ConversionError(MessageFormat.format(bundle.getString("is_not_a_valid_date"), value));
		}
	}

}