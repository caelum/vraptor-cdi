package br.com.caelum.example.controller;

import javax.inject.Inject;

import org.vraptor.Controller;
import org.vraptor.Result;

@Controller
public class MyController {

	@Inject
	private Result result;
	
	public void home() {
		result.forwardTo("/home.jsp");
	}
}
