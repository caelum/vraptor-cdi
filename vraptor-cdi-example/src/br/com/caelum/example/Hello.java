package br.com.caelum.example;

import java.io.IOException;

import javax.inject.Inject;

import org.vraptor.Path;
import org.vraptor.Controller;
import org.vraptor.Result;


@Controller
public class Hello {

	@Inject
	private ProductDAO dao;

	@Inject
	private Result result;
	
	public void index() throws IOException {
		dao.save(new Product());
		System.out.println("[Hello] Hello, VRaptor CDI. I'm index()");
		
		result.forwardTo("/home.jsp");
	}
	
	@Path("/custom")
	public void customPath() {
		System.out.println("[Hello] I'm customPath()");
		
		result.include("fromController", "[ This value comes from the Controller ]");
		result.forwardTo("/custom.jsp");
	}
}
