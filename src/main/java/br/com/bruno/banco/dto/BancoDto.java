package br.com.bruno.banco.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.bruno.banco.model.Banco;

public class BancoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotEmpty(message="Preenchimento obrigatório")
	@Length(min=3, max=250, message="O tamanho deve ser entre 3 e 250 caracteres")
	private String nome;

	public BancoDto() {
	}

	public BancoDto(Banco banco) {
		this.id = banco.getBancoId();
		this.nome = banco.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
