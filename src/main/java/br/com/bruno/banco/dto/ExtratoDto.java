package br.com.bruno.banco.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.bruno.banco.model.Conta;
import br.com.bruno.banco.model.Extrato;
import br.com.bruno.banco.service.util.ContaExtrato;

public class ExtratoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private String tipoOperacao;
	private Double valor;
	

	private String agenciaNumero;
	private String contaNumero;
	private String titular;
	private String informacoes;

	public ExtratoDto(Extrato extrato) {
		this.id = extrato.getExtratoId();
		this.tipoOperacao = extrato.getOperacao().name();
		this.valor = extrato.getValor();
		this.informacoes = extrato.getInformacoes();
		inicializaCampos(extrato.getConta());
	}

	public ExtratoDto(ContaExtrato extrato) {
		this.id = extrato.getExtrato().getExtratoId();
		this.tipoOperacao = extrato.getExtrato().getOperacao().getDescricao();
		this.valor = extrato.getExtrato().getValor();
		this.informacoes = extrato.getExtrato().getInformacoes();
		inicializaCampos(extrato.getConta());
	}
	/**
	 * @param conta
	 */
	private void inicializaCampos(Conta conta) {
		if (conta == null) {
			this.agenciaNumero = "";
			this.contaNumero = "";
			this.titular = "";
			return;
		}

		this.contaNumero = conta.getNumeroConta();

		if (conta.getAgencia() != null) {
			this.agenciaNumero = conta.getAgencia().getNumeroAgencia();
		}

		if (conta.getCliente() != null) {
			this.titular = conta.getCliente().getNome();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getInformacoes() {
		return informacoes;
	}

	public void setInformacoes(String informacoes) {
		this.informacoes = informacoes;
	}

	public String getAgenciaNumero() {
		return agenciaNumero;
	}

	public void setAgenciaNumero(String agenciaNumero) {
		this.agenciaNumero = agenciaNumero;
	}

	public String getContaNumero() {
		return contaNumero;
	}

	public void setContaNumero(String contaNumero) {
		this.contaNumero = contaNumero;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

}
