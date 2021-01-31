package br.com.bruno.banco.repository.customizer;

import java.util.List;
import java.util.Optional;

import br.com.bruno.banco.model.Cliente;
import br.com.bruno.banco.model.Conta;

public interface ContaRepositoryCustomize {

	public Optional<Conta> buscarPorNumero(String numero);
	public Optional<Conta> buscarPorNumeroAgenciaNumero(String contaNumero, String agenciaNumero);
	public Optional<Conta> buscarPorNumeroAgenciaNumeroBancoId(String contaNumero, String agenciaNumero, Long bancoId);
	public Optional<List<Conta>> buscarPorCliente(Cliente cliente);
}
