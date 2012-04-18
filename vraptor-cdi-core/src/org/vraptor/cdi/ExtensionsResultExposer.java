package org.vraptor.cdi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.vraptor.impl.ScannedControllerInterceptors;
import org.vraptor.impl.ScannedControllers;

@ApplicationScoped
public class ExtensionsResultExposer {

	@Produces @ApplicationScoped
	public ScannedControllers produce(ControllerScannerExtension extension) {
		return extension.controllers;
	}
	
	@Produces @ApplicationScoped
	public ScannedControllerInterceptors produce(ControllerInterceptorsExtension extension) {
		return extension.interceptors;
	}
}
