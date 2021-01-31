package br.com.bruno.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bruno.banco.model.Agencia;
import br.com.bruno.banco.repository.customizer.AgenciaRepositoryCustomizer;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long>, AgenciaRepositoryCustomizer{


}
