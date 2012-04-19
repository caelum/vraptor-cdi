package org.vraptor.impl.cdi;

import java.util.Set;

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
		Set<Bean<?>> beans = beanManager.getBeans(clazz);
		
		if (beans.size() == 0) {
			System.out.println("Couldn't find  any " + clazz.getName() + " with default qualifiers.");
		}
		
		Bean<T> bean = (Bean<T>) beans.iterator().next();
        CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, clazz, ctx);
	}
}
