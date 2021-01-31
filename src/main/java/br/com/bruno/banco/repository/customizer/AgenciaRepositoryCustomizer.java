package br.com.bruno.banco.repository.customizer;

import java.util.List;
import java.util.Optional;

import br.com.bruno.banco.model.Agencia;

public interface AgenciaRepositoryCustomizer {

	public Optional<Agencia> buscarPorNumero(Long bancoId, String numeroDaConta);
	public List<Agencia> buscarPorNumero(Long id);
}
