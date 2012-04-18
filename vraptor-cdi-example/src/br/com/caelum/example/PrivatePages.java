package br.com.caelum.example;

import javax.inject.Inject;

import org.vraptor.Controller;
import org.vraptor.Result;

@Admin
@Controller
public class PrivatePages {

	@Inject Result result;
	
	public void index() {
		result.text("Congratulations! Only admins can see this.");
	}
}
