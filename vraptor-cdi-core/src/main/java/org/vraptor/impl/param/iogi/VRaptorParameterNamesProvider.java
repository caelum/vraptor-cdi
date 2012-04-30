/**
 *
 */
package org.vraptor.impl.param.iogi;

import java.lang.reflect.AccessibleObject;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.vraptor.impl.param.ParameterNameProvider;

/**
 * An adapter for iogi's parameterNamesProvider
 *
 * @author Lucas Cavalcanti
 * @author Sérgio Lopes
 */
public class VRaptorParameterNamesProvider implements br.com.caelum.iogi.spi.ParameterNamesProvider {
	@Inject private ParameterNameProvider parameterNameProvider;

	public List<String> lookupParameterNames(AccessibleObject methodOrConstructor) {
		return Arrays.asList(parameterNameProvider.parameterNamesFor(methodOrConstructor));
	}
}