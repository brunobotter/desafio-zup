package br.com.bruno.banco.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.bruno.banco.dto.BancoDto;
import br.com.bruno.banco.exception.IntegridadeDeDadosException;
import br.com.bruno.banco.exception.ObjetoNaoEncontradoException;
import br.com.bruno.banco.model.Banco;
import br.com.bruno.banco.repository.BancoRepository;
import br.com.bruno.banco.service.util.ServiceGenericoImpl;
@Service
public class BancoService extends ServiceGenericoImpl<Banco, Long> {

	@Autowired
	private BancoRepository bancoRepository;

	public BancoService(BancoRepository bancoRepository) {
		super(bancoRepository);
		this.bancoRepository = bancoRepository;
	}

	@Override
	public Banco buscar(Long key) {
		Optional<Banco> banco = bancoRepository.findById(key);

		return banco.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado! Id: " + key + ", Tipo: " + Banco.class.getName()));
	}

	@Override
	public Banco atualizar(Banco novoBanco) {
		Banco banco = buscar(novoBanco.getBancoId());
		novoBanco = atualizaDados(banco, novoBanco);
		return super.atualizar(novoBanco);
	}

	@Override
	protected Banco atualizaDados(Banco entity, Banco newEntity) {
		Banco bancoAtualizado = new Banco();

		bancoAtualizado.setBancoId(entity.getBancoId());
		bancoAtualizado.setNome(newEntity.getNome());

		return bancoAtualizado;
	}

	@Override
	public void remover(Long key) {
		buscar(key);

		try {
			bancoRepository.deleteById(key);
		} catch (DataIntegrityViolationException e) {
			throw new IntegridadeDeDadosException("Não é possível excluir um banco que possuí agências!");
		}
	}

	public Banco converteDTOEmEntidade(BancoDto dto) {
		return new Banco(dto.getId(), dto.getNome());
	}

}
