package br.com.bruno.banco.service.util;

import java.io.Serializable;

import br.com.bruno.banco.model.Conta;
import br.com.bruno.banco.model.Extrato;

public class ContaExtrato implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Conta conta;
	private Extrato extrato;
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public Extrato getExtrato() {
		return extrato;
	}
	public void setExtrato(Extrato extrato) {
		this.extrato = extrato;
	}
	public ContaExtrato(Conta conta, Extrato extrato) {
		super();
		this.conta = conta;
		this.extrato = extrato;
	}



}
