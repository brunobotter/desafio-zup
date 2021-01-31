package br.com.bruno.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bruno.banco.model.Banco;
import br.com.bruno.banco.repository.customizer.BancoRepositoryCustomizer;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long>, BancoRepositoryCustomizer{

}
