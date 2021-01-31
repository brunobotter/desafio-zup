package br.com.bruno.banco.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bruno.banco.model.Cliente;
import br.com.bruno.banco.model.Conta;
import br.com.bruno.banco.repository.customizer.ContaRepositoryCustomize;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>, ContaRepositoryCustomize {

	Page<Conta> findByCliente(Cliente cliente, PageRequest pageRequest);

}
