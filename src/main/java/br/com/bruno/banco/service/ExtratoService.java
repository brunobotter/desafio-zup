package br.com.bruno.banco.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bruno.banco.model.Conta;
import br.com.bruno.banco.model.Extrato;
import br.com.bruno.banco.model.enums.Operacao;
import br.com.bruno.banco.repository.ExtratoRepository;
import br.com.bruno.banco.service.util.ServiceGenericoImpl;
@Service
public class ExtratoService extends ServiceGenericoImpl<Extrato, Long> {
	@Autowired
	private ExtratoRepository extratoRepository;


	@Autowired
	private UserService userService;

	public ExtratoService(ExtratoRepository extratoRepository) {
		super(extratoRepository);
		this.extratoRepository = extratoRepository;
	}

	@Override
	protected Extrato atualizaDados(Extrato entity, Extrato newEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Extrato gerar(Boolean credito, Conta conta, Operacao tipo, Double valor) {
		return gerar(credito, conta, tipo, valor, null);
	}

	public Extrato gerar(Boolean credito, Conta conta, Operacao tipo, Double valor, Conta contaDestino) {
		LocalDateTime data = LocalDateTime.now();

		Extrato extrato = new Extrato(data, tipo, valor, conta);
		extrato.setInformacoes(controiInformacoes(credito, conta, tipo, valor, contaDestino, data));
		extrato = extratoRepository.save(extrato);

		return extrato;
	}

	private String controiInformacoes(Boolean credito, Conta conta, Operacao tipo, Double valor, Conta contaDestino,
			LocalDateTime data) {
		if (credito) {
			return informacaoCredito(contaDestino, tipo, valor, conta, data);
		} else {
			return informacaoDebito(conta, tipo, valor, contaDestino, data);
		}
	}

	private String informacaoDebito(Conta conta, Operacao tipo, Double valor, Conta contaDestino, LocalDateTime data) {
		String dataFormatada = formatar(data);
		if (Operacao.TRANSFERENCIA.equals(tipo)) {
			return String.format("DATA: %s " + "TRANFERÊNCIA REALIZADA DE R$ %.2f " + "PARA %s, CONTA: %s AG: %s",
					dataFormatada, valor, contaDestino.getCliente().getNome().split(" ")[0],
					contaDestino.getNumeroConta(), contaDestino.getAgencia().getNumeroAgencia());
		} else {
			return String.format("DATA: %s " + "SAQUE DE R$ %.2f", dataFormatada, valor);
		}
	}

	private String informacaoCredito(Conta conta, Operacao tipo, Double valor, Conta contaDestino, LocalDateTime data) {
		String dataFormatada = formatar(data);
		if (Operacao.TRANSFERENCIA.equals(tipo)) {
			return String.format("DATA: %s " + "TRANFERÊNCIA RECEBIDA DE R$ %.2f " + "POR %s, CONTA: %s AG: %s",
					dataFormatada, valor, contaDestino.getCliente().getNome().split(" ")[0],
					contaDestino.getNumeroConta(), contaDestino.getAgencia().getNumeroAgencia());
		} else {
			return String.format("DATA: %s " + "DEPÓSITO DE R$ %.2f", dataFormatada, valor);
		}
	}

	public List<Extrato> listarTodosPorContaId(Long id) {
		userService.validaClienteConta(id);
		return extratoRepository.listarTodosPorContaId(id);
	}

	public Extrato buscar(Long id, Long extratoId) {
		userService.validaClienteConta(id);
		return buscar(extratoId);
	}
	
	public String formatar(LocalDateTime data) {


		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String agoraFormatado = data.format(formatter);
        return agoraFormatado;
	}
}
