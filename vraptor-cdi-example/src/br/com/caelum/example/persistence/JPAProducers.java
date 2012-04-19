package br.com.caelum.example.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import br.com.caelum.example.fake.EntityManager;
import br.com.caelum.example.fake.EntityManagerFactory;

@ApplicationScoped
public class JPAProducers {

	@Produces @ApplicationScoped
	public EntityManagerFactory produceFactory() {
		return new EntityManagerFactory();
	}
	
	@Produces @RequestScoped
	public EntityManager produceEM(EntityManagerFactory factory) {
		return factory.openEntityManager();
	}
	
	public void closeFactory(@Disposes EntityManagerFactory factory) {
		factory.close();
	}
	
	public void closeEM(@Disposes EntityManager entityManager) {
		entityManager.close();
	}
}
