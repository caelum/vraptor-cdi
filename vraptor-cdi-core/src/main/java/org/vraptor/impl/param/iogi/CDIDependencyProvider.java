/**
 *
 */
package org.vraptor.impl.param.iogi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.vraptor.impl.cdi.CDIUtils;

import br.com.caelum.iogi.reflection.Target;
import br.com.caelum.iogi.spi.DependencyProvider;

/**
 * an adapter for IOGI's dependency provider based on CDI's container
 *
 * @author Sérgio Lopes
 */
@ApplicationScoped
public class CDIDependencyProvider implements DependencyProvider {

	@Inject private CDIUtils utils;

	public boolean canProvide(Target<?> target) {
		Class<?> type = target.getClassType();
		return utils.hasBean(type);
	}

	public Object provide(Target<?> target) {
		Class<?> type = target.getClassType();
		return utils.getResourceInstanceFromCDI(type);
	}
}