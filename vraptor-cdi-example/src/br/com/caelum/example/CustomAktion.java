package br.com.caelum.example;

import java.io.IOException;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.vraptor.Controller;
import org.vraptor.extensions.RemovableControllerExtension;

@Controller
public class CustomAktion {

	@Produces @RemovableControllerExtension
	public static final String aktion = "Aktion";
	
	@Inject
	private HttpServletResponse response;
	
	public void extension() throws IOException {
		response.getWriter().println("This action has a pluggable custom bizarre extension. AKTION");
	}
	
}
