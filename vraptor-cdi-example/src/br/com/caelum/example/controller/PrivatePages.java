package br.com.caelum.example.controller;

import javax.inject.Inject;

import org.vraptor.Controller;
import org.vraptor.Result;

import br.com.caelum.example.interceptor.Admin;

@Admin
@Controller
public class PrivatePages {

	@Inject Result result;
	
	public void index() {
		result.text("Congratulations! Only admins can see this.");
	}
}
