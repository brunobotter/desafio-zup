package br.com.bruno.banco.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import br.com.bruno.banco.model.Agencia;
import br.com.bruno.banco.model.Banco;

public class AgenciaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Pattern(regexp = "\\d{4}-\\d{1}", message = "O número da agência deve possuir o seguinte formato 0000-0 e ser formado apenas por números!")
	private String numeroAgencia;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 3, max = 250, message = "O tamanho deve ser entre 3 e 250 caracteres")
	private String nomeAgencia;

	private Banco banco;

	public AgenciaDto() {
		super();
	}

	public AgenciaDto(Agencia agencia) {
		super();
		this.numeroAgencia = agencia.getNumeroAgencia();
		this.nomeAgencia = agencia.getNomeAgencia();
		this.banco = agencia.getBanco();
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
