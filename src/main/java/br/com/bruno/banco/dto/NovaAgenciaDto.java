package br.com.bruno.banco.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import br.com.bruno.banco.model.Agencia;

public class NovaAgenciaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Pattern(regexp = "\\d{4}-\\d{1}", message = "O número da agência deve possuir o seguinte formato 0000-0 e ser formado apenas por números!")
	private String numero;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 3, max = 250, message = "O tamanho deve ser entre 3 e 250 caracteres")
	private String nome;

	@NotNull(message = "Preenchimento obrigatório")
	private Long bancoId;

	public NovaAgenciaDto(Agencia agencia) {
		this.numero = agencia.getNumeroAgencia();
		this.nome = agencia.getNomeAgencia();
		this.bancoId = agencia.getBanco().getBancoId();
	}

	public NovaAgenciaDto() {
		super();
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getBancoId() {
		return bancoId;
	}

	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
	}

}
