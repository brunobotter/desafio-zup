package br.com.bruno.banco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.bruno.banco.dto.AgenciaDto;
import br.com.bruno.banco.dto.NovaAgenciaDto;
import br.com.bruno.banco.exception.IntegridadeDeDadosException;
import br.com.bruno.banco.exception.NegocioException;
import br.com.bruno.banco.exception.ObjetoNaoEncontradoException;
import br.com.bruno.banco.model.Agencia;
import br.com.bruno.banco.model.Banco;
import br.com.bruno.banco.repository.AgenciaRepository;
import br.com.bruno.banco.service.util.ServiceGenericoImpl;

@Service
public class AgenciaService extends ServiceGenericoImpl<Agencia, Long> {

	@Autowired
	private AgenciaRepository agenciaRepository;

	public AgenciaService(AgenciaRepository agenciaRepository) {
		super(agenciaRepository);
		this.agenciaRepository = agenciaRepository;
	}

	@Override
	public Agencia buscar(Long key) {
		Optional<Agencia> agencia = agenciaRepository.findById(key);

		return agencia.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado! Id: " + key + ", Tipo: " + Agencia.class.getName()));
	}

	@Override
	public Agencia atualizar(Agencia novoAgencia) {
		Agencia agencia = buscar(novoAgencia.getAgenciaId());
		novoAgencia = atualizaDados(agencia, novoAgencia);
		return agenciaRepository.save(novoAgencia);
	}

	@Override
	protected Agencia atualizaDados(Agencia entity, Agencia newEntity) {
		Agencia agencia = new Agencia(newEntity.getNumeroAgencia(), newEntity.getNomeAgencia());
		agencia.setAgenciaId(entity.getAgenciaId());
		agencia.setBanco(entity.getBanco());
		Optional<Agencia> agenciaExite = agenciaRepository.buscarPorNumero(entity.getBanco().getBancoId(), entity.getNumeroAgencia());
		if(agenciaExite.isEmpty()) {
			return agencia;
		}else {
			throw new NegocioException("Agencia ja cadastrada");
		}
		
	}

	public Agencia salvar(Agencia entity) {
		Optional<Agencia> agencia = agenciaRepository.buscarPorNumero(entity.getBanco().getBancoId(), entity.getNumeroAgencia());
		if(agencia.isEmpty()) {
			return agenciaRepository.save(entity);
		}else {
			throw new NegocioException("Agencia ja cadastrada");
		}
	}

	@Override
	public void remover(Long key) {
		buscar(key);

		try {
			agenciaRepository.deleteById(key);
		} catch (DataIntegrityViolationException e) {
			throw new IntegridadeDeDadosException("Não é possível excluir a agência que possuí contas!");
		}
	}

	public Agencia converteDTOEmEntidade(AgenciaDto agenciaDto) {
		return new Agencia(agenciaDto.getNumeroAgencia(), agenciaDto.getNomeAgencia());
	}

	public Agencia converteDTOEmEntidade(NovaAgenciaDto agenciaDto) {
		Agencia agencia = new Agencia(agenciaDto.getNumero(), agenciaDto.getNome());
		Banco banco = new Banco(agenciaDto.getBancoId());
		agencia.setBanco(banco);

		return agencia;
	}

	public Agencia buscarPorNumero(Long bancoId, String numero) {
		Optional<Agencia> agencia = agenciaRepository.buscarPorNumero(bancoId, numero);

		return agencia.orElseThrow(() -> new ObjetoNaoEncontradoException("Agência não encontrada! Numero: " + numero));
	}

	public List<Agencia> buscarPorBanco(Long bancoId) {
		List<Agencia> agencia = agenciaRepository.buscarPorNumero(bancoId);
		if (agencia.isEmpty()) {
			throw new ObjetoNaoEncontradoException("Nenhuma agencia encontrada");
		}
		return agencia;
	}

}
