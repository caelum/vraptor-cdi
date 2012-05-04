package org.vraptor.impl.cdi;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.vraptor.VRaptorException;

@SuppressWarnings("unchecked")
@ApplicationScoped
public class CDIUtils {

	@Inject
	private BeanManager beanManager;
	
	public boolean hasBean(Class<?> clazz) {
		return beanManager.getBeans(clazz).size() != 0;
	}
	
	public <T> T getResourceInstanceFromCDI(Class<T> clazz) {
		Bean<T> bean = findBean(clazz);
        return instantiate(clazz, bean);
	}

	private <T> T instantiate(Class<T> clazz, Bean<T> bean) {
		CreationalContext<T> ctx = beanManager.createCreationalContext(bean);
        return (T) beanManager.getReference(bean, clazz, ctx);
	}

	private <T> Bean<T> findBean(Class<T> clazz) {
		Set<Bean<?>> beans = beanManager.getBeans(clazz);
		
		if (beans.isEmpty()) {
			new VRaptorException("Couldn't find  any " + clazz.getName() + " with default qualifiers.");
		}
		
		return (Bean<T>) beans.iterator().next();
	}
}
