package br.com.caelum.example.fake;

import javax.enterprise.inject.Alternative;

@Alternative
public class EntityManagerFactory {

	public EntityManagerFactory() {
		System.out.println("Open EntityManagerFactory");
	}
	
	public void close() {
		System.out.println("Closing EntityManagerFactory");
	}

	public EntityManager openEntityManager() {
		System.out.println("New EntityManager");
		return new EntityManager();
	}

}
