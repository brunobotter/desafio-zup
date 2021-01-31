package br.com.bruno.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bruno.banco.model.Cliente;
import br.com.bruno.banco.repository.customizer.Clienterepositorystomizer;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, Clienterepositorystomizer{

}
