package br.com.bruno.banco.repository.customizer;

import java.util.Optional;

import br.com.bruno.banco.model.Cliente;

public interface Clienterepositorystomizer {

	public Optional<Cliente> buscarPorCpf(String cpf);
	public Optional<Cliente> buscaPorEmail(String email);
}
