package br.com.bruno.banco.repository.impl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.bruno.banco.model.Cliente;
import br.com.bruno.banco.repository.customizer.Clienterepositorystomizer;

public class ClienteRepositoryImpl implements Clienterepositorystomizer {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<Cliente> buscarPorCpf(String cpf) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);

		Root<Cliente> root = cq.from(Cliente.class);
		CriteriaQuery<Cliente> query = cq.select(root);

		Predicate predicado = cb.equal(root.get("cpf"), cpf);

		Predicate[] predicates = { predicado };

		query.where(predicates);

		TypedQuery<Cliente> tq = entityManager.createQuery(query);

		try {
			return Optional.of(tq.getSingleResult());
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Optional<Cliente> buscaPorEmail(String email) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);

		Root<Cliente> root = cq.from(Cliente.class);
		CriteriaQuery<Cliente> query = cq.select(root);

		Predicate predicado = cb.equal(root.get("email"), email);

		Predicate[] predicates = { predicado };

		query.where(predicates);

		TypedQuery<Cliente> tq = entityManager.createQuery(query);

		try {
			return Optional.of(tq.getSingleResult());
		} catch (NoResultException e) {
			return null;
		}
	}
}
