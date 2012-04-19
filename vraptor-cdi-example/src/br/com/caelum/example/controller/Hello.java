package br.com.caelum.example.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.vraptor.Path;
import org.vraptor.Controller;
import org.vraptor.Result;
import org.vraptor.http.RequestParam;

import br.com.caelum.example.model.Product;
import br.com.caelum.example.persistence.ProductDAO;


@Controller
public class Hello {

	@Inject
	private ProductDAO dao;

	@Inject
	private Result result;
	
	@Inject @RequestParam(defaultValue="This value comes from the Controller")
	private String message;
	
	@Inject @RequestParam("product.id")
	private String productId;
	
	public void index() throws IOException {
		dao.save(new Product());
		System.out.println("[Hello] Hello, VRaptor CDI. I'm index()");
		
		result.forwardTo("/home.jsp");
	}
	
	@Path("/custom")
	public void customPath() {
		System.out.println("[Hello] I'm customPath()");
		
		result.include("fromController", "[ " + message + " ] " + productId);
		result.forwardTo("/custom.jsp");
	}
}
