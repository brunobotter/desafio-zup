package br.com.bruno.banco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.bruno.banco.dto.AtualizarContaDto;
import br.com.bruno.banco.dto.ContaDto;
import br.com.bruno.banco.dto.NovaContaDto;
import br.com.bruno.banco.dto.SaqueDepositoDto;
import br.com.bruno.banco.dto.TransferenciaDto;
import br.com.bruno.banco.exception.IntegridadeDeDadosException;
import br.com.bruno.banco.exception.NegocioException;
import br.com.bruno.banco.exception.ObjetoNaoEncontradoException;
import br.com.bruno.banco.model.Agencia;
import br.com.bruno.banco.model.Cliente;
import br.com.bruno.banco.model.Conta;
import br.com.bruno.banco.model.Extrato;
import br.com.bruno.banco.model.enums.Operacao;
import br.com.bruno.banco.model.enums.Role;
import br.com.bruno.banco.model.enums.TipoConta;
import br.com.bruno.banco.repository.ContaRepository;
import br.com.bruno.banco.service.util.ContaExtrato;
import br.com.bruno.banco.service.util.ServiceGenericoImpl;

@Service
public class ContaService extends ServiceGenericoImpl<Conta, Long> {
	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private AgenciaService agenciaService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private BancoService bancoService;

	@Autowired
	private ExtratoService extratoService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	public ContaService(ContaRepository contaRepository) {
		super(contaRepository);
		this.contaRepository = contaRepository;
	}

	@Override
	public List<Conta> listarTodos() {
		return buscarTodos().get();
	}

	public Optional<List<Conta>> buscarTodos() {
		if (userService.hasRole(Role.ADMIN)) {
			return Optional.of(super.listarTodos());
		}

		return contaRepository.buscarPorCliente(userService.getUserDetails().getCliente());
	}

	@Override
	public Page<Conta> buscaPaginada(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		if (userService.hasRole(Role.ADMIN)) {
			return contaRepository.findAll(pageRequest);
		}

		return contaRepository.findByCliente(userService.getUserDetails().getCliente(), pageRequest);
	}

	@Override
	public Conta buscar(Long key) {
		Optional<Conta> conta = contaRepository.findById(key);

		userService.validaClienteConta(key);

		return conta.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado! Id: " + key + ", Tipo: " + Conta.class.getName()));
	}
	
	public Conta buscaPorId(Long key) {
		Optional<Conta> conta = contaRepository.findById(key);

		return conta.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Objeto não encontrado! Id: " + key + ", Tipo: " + Conta.class.getName()));
	}

	@Override
	public Conta atualizar(Conta novoConta) {
		Conta conta = buscar(novoConta.getContaId());
		novoConta = atualizaDados(conta, novoConta);
		return contaRepository.save(novoConta);
	}

	@Override
	protected Conta atualizaDados(Conta entity, Conta newEntity) {
		Conta conta = new Conta(newEntity.getNumeroConta(), newEntity.getTipo(), newEntity.getSaldo());
		conta.setContaId(entity.getContaId());
		conta.setCliente(entity.getCliente());
		conta.setAgencia(entity.getAgencia());

		return conta;
	}

	@Override
	public void remover(Long key) {
		buscar(key);

		try {
			contaRepository.deleteById(key);
		} catch (DataIntegrityViolationException e) {
			throw new IntegridadeDeDadosException("Não é possível excluir a conta que possuí contas!");
		}
	}

	public Conta converteDTOEmEntidade(ContaDto contaDto) {
		return new Conta(contaDto.getNumero(), TipoConta.valueOf(contaDto.getTipo()), contaDto.getSaldo());
	}

	public Conta converteDTOEmEntidade(AtualizarContaDto atualizaContaDto) {
		return new Conta(atualizaContaDto.getNumeroConta(), TipoConta.valueOf(atualizaContaDto.getTipo()),
				atualizaContaDto.getSaldo());
	}

	public Conta converteDTOEmEntidade(NovaContaDto novaContaDto) {
		Conta conta = new Conta(novaContaDto.getNumero(), TipoConta.valueOf(novaContaDto.getTipo()),
				novaContaDto.getSaldo());

		Cliente cliente = new Cliente(novaContaDto.getNome(), novaContaDto.getCpf(),
				bCryptPasswordEncoder.encode(novaContaDto.getSenha()), novaContaDto.getEmail(),
				novaContaDto.getDataNascimento());
		Agencia agencia = agenciaService.buscarPorNumero(novaContaDto.getBancoId(), novaContaDto.getAgenciaNumero());

		conta.setCliente(cliente);
		conta.setAgencia(agencia);

		return conta;
	}

	public Conta buscarPorNumero(String numero) {
		Optional<Conta> conta = contaRepository.buscarPorNumero(numero);

		return conta.orElseThrow(() -> new ObjetoNaoEncontradoException("Conta não encontrada! número: " + numero));
	}

	public Conta buscarPorNumeroAgenciaNumeroBancoId(String contaNumero, String agenciaNumero, Long bancoId) {
		Optional<Conta> conta = contaRepository.buscarPorNumeroAgenciaNumeroBancoId(contaNumero, agenciaNumero,
				bancoId);

		return conta.orElseThrow(() -> new ObjetoNaoEncontradoException(
				"Conta não encontrada! número: " + contaNumero + ", na agência de número: " + agenciaNumero));
	}

	public Conta cadastrar(Conta conta) {

		try {
			verificaConta(conta.getNumeroConta(), conta.getAgencia().getNumeroAgencia(),
					conta.getAgencia().getBanco().getBancoId());
		} catch (ObjetoNaoEncontradoException e) {
			// Se cliente não existir persiste um novo, caso exista buscar para adicionar

			Cliente cliente = clienteService.salvar(conta.getCliente());
			Conta novaConta = new Conta(conta.getNumeroConta(), conta.getTipo(), cliente, conta.getAgencia(),
					conta.getSaldo());

			return salvar(novaConta);
		}

		throw new NegocioException("Já existe uma conta cadastrada na agência com o número informado!");
	}

	/**
	 * Verifica a existência do banco e agência. Caso existam verifica se existe uma
	 * conta com o {@linkplain contaNumero} nesse banco e agência.
	 * 
	 * @param contaNumero   Número da cnta que se deseja validar e buscar.
	 * @param agenciaNumero Agência que a conta pertence.
	 * @param bancoId       Banco que possuí a agência.
	 * @return
	 */
	public Conta verificaConta(String contaNumero, String agenciaNumero, Long bancoId) {
		// Verifiar se banco existe
		bancoService.buscar(bancoId);

		// Verificar se existe a agência
		agenciaService.buscarPorNumero(bancoId, agenciaNumero);

		// Verificar se existe a conta com os dados informados
		Conta conta = buscarPorNumeroAgenciaNumeroBancoId(contaNumero, agenciaNumero, bancoId);

		return conta;
	}

	public ContaExtrato sacar(SaqueDepositoDto saqueDto) {
		Conta conta = verificaConta(saqueDto.getContaNumero(), saqueDto.getAgenciaNumero(), saqueDto.getBancoId());

		userService.validaClienteConta(conta);

		Extrato extrato = debitar(conta, saqueDto.getValor());

		return new ContaExtrato(conta, extrato);
	}

	public ContaExtrato depositar(SaqueDepositoDto depositoDTO) {
		Conta conta = verificaConta(depositoDTO.getContaNumero(), depositoDTO.getAgenciaNumero(),
				depositoDTO.getBancoId());

		Extrato extrato = creditar(conta, depositoDTO.getValor());

		return new ContaExtrato(conta, extrato);
	}

	public ContaExtrato transferir(TransferenciaDto transferenciaDTO) {
		Conta contaOrigem = verificaConta(transferenciaDTO.getContaOrigemNumero(),
				transferenciaDTO.getAgenciaOrigemNumero(), transferenciaDTO.getBancoOrigemId());

		userService.validaClienteConta(contaOrigem);

		Conta contaDestino = verificaConta(transferenciaDTO.getContaDestinoNumero(),
				transferenciaDTO.getAgenciaDestinoNumero(), transferenciaDTO.getBancoDestinoId());

		ContaExtrato extrato = transferir(contaOrigem, contaDestino, transferenciaDTO.getValor());

		return extrato;
	}

	private Extrato debitar(Conta conta, Double valor) {
		return debitar(conta, valor, null, null);
	}

	private Extrato debitar(Conta conta, Double valor, Operacao tipo, Conta contaDestino) {
		if (valor <= conta.getSaldo()) {
			conta.setSaldo(conta.getSaldo() - valor);
			conta = contaRepository.save(conta);

			Extrato extratoDebito = extratoService.gerar(false, conta, tipo == null ? Operacao.SAQUE : tipo, valor,
					contaDestino);

			return extratoDebito;
		}

		throw new NegocioException(String.format(
				"A conta não possuí saldo suficiente para essa operação! Saldo: {0}, valor a ser debitado {1}",
				conta.getSaldo(), valor));
	}

	private Extrato creditar(Conta conta, Double valor) {
		return creditar(conta, valor, null, null);
	}

	private Extrato creditar(Conta conta, Double valor, Operacao tipo, Conta contaOrigem) {
		conta.setSaldo(conta.getSaldo() + valor);
		conta = contaRepository.save(conta);

		Extrato extratoCredito = extratoService.gerar(true, conta, tipo == null ? Operacao.DEPOSITO : tipo, valor,
				contaOrigem);

		return extratoCredito;
	}

	private ContaExtrato transferir(Conta contaOrigem, Conta contaDestino, Double valor) {
		Extrato extratoDebito = debitar(contaOrigem, valor, Operacao.TRANSFERENCIA, contaDestino);
		/** Extrato extratoCredito = **/
		creditar(contaDestino, valor, Operacao.TRANSFERENCIA, contaOrigem);

		return new ContaExtrato(contaOrigem, extratoDebito);
	}
}
