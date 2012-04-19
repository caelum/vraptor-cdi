package br.com.caelum.example.controller;

import javax.inject.Inject;

import org.vraptor.RootController;
import org.vraptor.Result;

@RootController
public class Main {
	
	@Inject
	private Result result;
	
	public void index() {
		System.out.println("App Root!");
		result.forwardTo("/main.jsp");
	}
}