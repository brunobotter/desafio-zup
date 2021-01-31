package br.com.bruno.banco.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import br.com.bruno.banco.model.Agencia;
import br.com.bruno.banco.model.Banco;

public class ListaAgenciaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long agenciaId;

	@Pattern(regexp = "\\d{4}-\\d{1}", message = "O número da agência deve possuir o seguinte formato 0000-0 e ser formado apenas por números!")
	private String numeroAgencia;

	private String nomeAgencia;

	private Banco banco;
	
	public ListaAgenciaDto(Agencia agencia) {
		super();
		this.agenciaId = agencia.getAgenciaId();
		this.numeroAgencia = agencia.getNumeroAgencia();
		this.nomeAgencia = agencia.getNomeAgencia();
		this.banco = agencia.getBanco();
	}

	public Long getAgenciaId() {
		return agenciaId;
	}

	public void setAgenciaId(Long agenciaId) {
		this.agenciaId = agenciaId;
	}

	public String getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	public String getNomeAgencia() {
		return nomeAgencia;
	}

	public void setNomeAgencia(String nomeAgencia) {
		this.nomeAgencia = nomeAgencia;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}



}
