package br.com.bruno.banco.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.bruno.banco.repository.customizer.BancoRepositoryCustomizer;

public class BancoRepositoryImpl implements BancoRepositoryCustomizer {

	@PersistenceContext
	private EntityManager entityManager;

}
