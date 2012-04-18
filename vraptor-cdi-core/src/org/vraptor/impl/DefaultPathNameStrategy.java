package org.vraptor.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vraptor.MainController;
import org.vraptor.extensions.PathNameStrategy;
import org.vraptor.extensions.RemovableControllerExtension;

/**
 * Hello#hi                ->  /hello/hi
 * MyController#home       ->  /my/home
 * Hello#index             ->  /hello
 * [@MainController]#page  ->  /page
 * [@MainController]#index ->  /
 *  
 * @author sergio
 */
@ApplicationScoped
public class DefaultPathNameStrategy implements PathNameStrategy {

	@Inject @RemovableControllerExtension
	private Instance<String> extensions;
	
	@Override
	public String pathFor(Route route) {
		String baseName = route.getControllerClass().getSimpleName();
		String methodName = route.getControllerMethod().getName();

		if (route.getControllerClass().isAnnotationPresent(MainController.class)) {
			baseName = null;
		} else {
			
			// removes extensions
			for (String extension : extensions) {
				if (baseName.equals(extension)) {
					throw new RuntimeException("Class " + route.getClass().getName() + " has a simple name exactly equals a removable extension.");
				}
				
				if (baseName.endsWith(extension)) {
					baseName = baseName.substring(0, baseName.length() - extension.length());
				}
			}
			baseName = normalizes(baseName);
		}
		
		return "/" + (baseName == null ? "" : baseName) + (methodName.equals("index")? "" : "/" + methodName);
	}

	private String normalizes(String baseName) {
		return baseName.substring(0,1).toLowerCase() + (baseName.length() > 1? baseName.substring(1) : ""); 
	}

}
