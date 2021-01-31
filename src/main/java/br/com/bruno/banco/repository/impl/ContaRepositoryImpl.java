package br.com.bruno.banco.repository.impl;

import java.util.List;
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
import br.com.bruno.banco.model.Conta;
import br.com.bruno.banco.repository.customizer.ContaRepositoryCustomize;

public class ContaRepositoryImpl implements ContaRepositoryCustomize {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Optional<Conta> buscarPorNumero(String numeroConta) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Conta> cq = cb.createQuery(Conta.class);

		Root<Conta> root = cq.from(Conta.class);
		CriteriaQuery<Conta> query = cq.select(root);

		Predicate predicado = cb.equal(root.get("numeroConta"), numeroConta);

		Predicate[] predicates = { predicado };

		query.where(predicates);

		TypedQuery<Conta> tq = entityManager.createQuery(query);

		try {
			return Optional.of(tq.getSingleResult());
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Optional<Conta> buscarPorNumeroAgenciaNumero(String contaNumero, String agenciaNumero) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Conta> cq = cb.createQuery(Conta.class);

		Root<Conta> root = cq.from(Conta.class);
		CriteriaQuery<Conta> query = cq.select(root);

		Predicate predicadoNumeroAgencia = cb.equal(root.get("agencia").get("numeroAgencia"), agenciaNumero);
		Predicate predicado = cb.equal(root.get("numeroConta"), contaNumero);

		Predicate[] predicates = { predicadoNumeroAgencia, predicado };

		query.where(predicates);

		TypedQuery<Conta> tq = entityManager.createQuery(query);

		try {
			return Optional.of(tq.getSingleResult());
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Optional<Conta> buscarPorNumeroAgenciaNumeroBancoId(String contaNumero, String agenciaNumero, Long bancoId) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Conta> cq = cb.createQuery(Conta.class);

		Root<Conta> root = cq.from(Conta.class);
		CriteriaQuery<Conta> query = cq.select(root);

		Predicate predicadoNumeroAgencia = cb.equal(root.get("agencia").get("numeroAgencia"), agenciaNumero);
		Predicate predicadoBancoId = cb.equal(root.get("agencia").get("banco").get("bancoId"), bancoId);
		Predicate predicado = cb.equal(root.get("numeroConta"), contaNumero);

		Predicate[] predicates = { predicadoNumeroAgencia, predicado, predicadoBancoId };

		query.where(predicates);

		TypedQuery<Conta> tq = entityManager.createQuery(query);

		try {
			return Optional.of(tq.getSingleResult());
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Optional<List<Conta>> buscarPorCliente(Cliente cliente) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Conta> cq = cb.createQuery(Conta.class);

		Root<Conta> root = cq.from(Conta.class);
		CriteriaQuery<Conta> query = cq.select(root);

		Predicate predicado = cb.equal(root.get("cliente").get("clienteId"), cliente.getClienteId());

		Predicate[] predicates = { predicado };

		query.where(predicates);

		TypedQuery<Conta> tq = entityManager.createQuery(query);

		try {
			return Optional.of(tq.getResultList());
		} catch (NoResultException e) {
			return null;
		}
	}

}
