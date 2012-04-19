package br.com.caelum.example.persistence;

import javax.inject.Inject;

import br.com.caelum.example.fake.EntityManager;
import br.com.caelum.example.model.Product;

public class ProductDAO {

	@Inject
	private EntityManager entityManager;
	
	public void save(Product p) {
		entityManager.persist(p);
	}
}
