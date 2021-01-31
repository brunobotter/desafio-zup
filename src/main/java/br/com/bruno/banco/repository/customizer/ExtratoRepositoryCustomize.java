package br.com.bruno.banco.repository.customizer;

import java.util.List;

import br.com.bruno.banco.model.Extrato;

public interface ExtratoRepositoryCustomize {

	public List<Extrato> listarTodosPorContaId(Long id);
}
