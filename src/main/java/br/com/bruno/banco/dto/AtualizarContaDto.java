package br.com.bruno.banco.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import br.com.bruno.banco.model.Conta;

public class AtualizarContaDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Pattern(regexp="\\d{5}-\\d{1}", message="O número da conta deve possuir o seguinte formato 00000-0 e ser formado apenas por números!")
	private String numeroConta;
	
	@NotEmpty(message="Preenchimento obrigatório")
	private String tipo;
	
	@Positive
	@NotNull(message="Preenchimento obrigatório")
	private Double saldo;

	public AtualizarContaDto() {
		
	}
	
	public AtualizarContaDto(Conta conta) {
		super();
		this.numeroConta = conta.getNumeroConta();
		this.tipo = conta.getTipo().name();
		this.saldo = conta.getSaldo();
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
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

	
}
