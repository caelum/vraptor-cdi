package org.vraptor.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

@ApplicationScoped
public class CDIUtils {

	@Inject
	private BeanManager beanManager;
	
	@SuppressWarnings("unchecked")
	public <T> T getResourceInstanceFromCDI(Class<T> clazz) {
		// create instance
		Bean<T> bean = (Bean<T>) beanManager.getBeans(clazz).iterator().next();
        CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, clazz, ctx);
	}
}
