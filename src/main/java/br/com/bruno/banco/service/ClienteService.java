package br.com.bruno.banco.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bruno.banco.dto.NovoClienteDto;
import br.com.bruno.banco.exception.NegocioException;
import br.com.bruno.banco.exception.ObjetoNaoEncontradoException;
import br.com.bruno.banco.model.Cliente;
import br.com.bruno.banco.repository.ClienteRepository;
import br.com.bruno.banco.service.util.ServiceGenericoImpl;

@Service
public class ClienteService extends ServiceGenericoImpl<Cliente, Long> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private UserService userService;

	public ClienteService(ClienteRepository clienteRepository) {
		super(clienteRepository);
		this.clienteRepository = clienteRepository;
	}

	@Override
	public Cliente salvar(Cliente entity) {
		Cliente clienteBuscado = null;

		try {
			cpfexiste(entity.getCpf());
			emailExiste(entity.getEmail());
			clienteBuscado = buscarPorCpf(entity.getCpf());

		} catch (ObjetoNaoEncontradoException e) {
			clienteBuscado = clienteRepository.save(entity);

		}

		return clienteBuscado;
	}

	@Override
	protected Cliente atualizaDados(Cliente entity, Cliente newEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente buscar(Long key) {
		userService.validaClienteId(key);
		return super.buscar(key);
	}

	public Cliente buscarPorCpf(String cpf) {
		userService.validaClienteCpf(cpf);

		Optional<Cliente> cliente = clienteRepository.buscarPorCpf(cpf);

		return cliente.orElseThrow(() -> new ObjetoNaoEncontradoException("Cliente não encontrado! CPF: " + cpf));
	}

	public boolean emailExiste(String email) {

		Optional<Cliente> cliente = clienteRepository.buscaPorEmail(email);
		if (cliente.isEmpty()) {
			return true;
		} else {
			throw new NegocioException("Email ja cadastrado no sistema");
		}
	}

	public boolean cpfexiste(String cpf) {

		Optional<Cliente> cliente = clienteRepository.buscarPorCpf(cpf);
		if (cliente.isEmpty()) {
			return true;
		} else {
			throw new NegocioException("CPF já cadastrado no sistema");
		}
	}

	public Cliente converteNovoClienteDTOEmEntidade(@Valid NovoClienteDto novoClienteDto) {
		return new Cliente(novoClienteDto);
	}

}
