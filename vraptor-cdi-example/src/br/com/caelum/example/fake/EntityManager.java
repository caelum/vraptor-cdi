package br.com.caelum.example.fake;

import javax.enterprise.inject.Alternative;

@Alternative
public class EntityManager {

	public void close() {
		System.out.println("Closing EntityManager");
	}

	public void persist(Object o) {
		System.out.println("Persisting " + o);
	}

}
