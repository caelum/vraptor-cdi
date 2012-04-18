package br.com.caelum.example;

import javax.inject.Inject;

import org.vraptor.MainController;
import org.vraptor.Result;

@MainController
public class Main {
	
	@Inject
	private Result result;
	
	public void index() {
		System.out.println("App Root!");
		result.forwardTo("/main.jsp");
	}
}
