package br.com.bruno.banco.dto;

import java.io.Serializable;

import javax.validation.constraints.Negative;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.bruno.banco.model.Conta;

public class ContaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	

	@NotEmpty(message = "Preenchimento obrigatório")
	@Pattern(regexp = "\\d{5}-\\d{1}", message = "O número da conta deve possuir o seguinte formato 00000-0 e ser formado apenas por números!")
	private String numero;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String tipo;

	@Negative
	@NotNull(message = "Preenchimento obrigatório")
	private Double saldo;

	private String bancoNome;

	private String agenciaNome;

	private String agenciaNumero;

	public ContaDto(Conta conta) {
		this.id = conta.getContaId();
		this.numero = conta.getNumeroConta();
		this.tipo = conta.getTipo().name();
		this.saldo = conta.getSaldo();

		inicializaCampos(conta);
	}

	private void inicializaCampos(Conta conta) {
		if (conta.getAgencia() != null) {
			this.agenciaNome = conta.getAgencia().getNomeAgencia();
			this.agenciaNumero = conta.getAgencia().getNumeroAgencia();

			if (conta.getAgencia().getBanco() != null) {
				this.bancoNome = conta.getAgencia().getBanco().getNome();
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getBancoNome() {
		return bancoNome;
	}

	public void setBancoNome(String bancoNome) {
		this.bancoNome = bancoNome;
	}

	public String getAgenciaNome() {
		return agenciaNome;
	}

	public void setAgenciaNome(String agenciaNome) {
		this.agenciaNome = agenciaNome;
	}

	public String getAgenciaNumero() {
		return agenciaNumero;
	}

	public void setAgenciaNumero(String agenciaNumero) {
		this.agenciaNumero = agenciaNumero;
	}

}
