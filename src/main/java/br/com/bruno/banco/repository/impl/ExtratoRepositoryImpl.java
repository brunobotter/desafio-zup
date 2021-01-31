package br.com.bruno.banco.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.bruno.banco.model.Extrato;
import br.com.bruno.banco.repository.customizer.ExtratoRepositoryCustomize;

public class ExtratoRepositoryImpl implements ExtratoRepositoryCustomize {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Extrato> listarTodosPorContaId(Long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Extrato> cq = cb.createQuery(Extrato.class);

		Root<Extrato> root = cq.from(Extrato.class);
		CriteriaQuery<Extrato> query = cq.select(root);

		Predicate predicado = cb.equal(root.get("conta").get("contaId"), id);

		Predicate[] predicates = { predicado };

		query.where(predicates);

		TypedQuery<Extrato> tq = entityManager.createQuery(query);

		try {
			return tq.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
