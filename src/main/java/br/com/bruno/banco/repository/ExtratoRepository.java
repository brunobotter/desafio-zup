package br.com.bruno.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bruno.banco.model.Extrato;
import br.com.bruno.banco.repository.customizer.ExtratoRepositoryCustomize;

@Repository
public interface ExtratoRepository extends JpaRepository<Extrato, Long>, ExtratoRepositoryCustomize {

}
